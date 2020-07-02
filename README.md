# E-Commerce (EIMT) Mini Project
## JSP Trams using Domain Driven Design
### Anton Kahwaji 161555
This project is for the E-Commerce course from the Faculty of Computer Science and Engineering.

Here we imagine that [JSP](http://www.jsp.com.mk/) started using Trams as a modern method of transport.

## Usage
### Launching
- Using docker, enter the folder `./docker` and execute `docker-compose up`.
    - This launches a RabbitMQ instance and 3 PostgreSQL databases.
- Then in Intellij, the Spring Applications should be launched.
    - First `SharedKernelApplication` should be launched since it creates the queues in RabbitMQ used by the application.
    - The other applications can be started together in any order.
        - `TimeManagementApplication`: operates a custom clock that is used by the other modules so the application can travel in time for development and testing purposes.
        - `TicketManagementApplication`: takes care of all the tickets, from offering plan to selling tickets to keeping track of each customer's wallet.
        - `RouteManagementApplication`: keeps track of different predefined routes, also stores meta information about rides.
        - `RideManagementApplication`: does the main work, it checks rides when a conductor tells it so, for this, it communicates with the other modules.
- A frontend application in React can also be started by simply running `npm start` (and `npm install` if needed).

### Navigating
- The frontend app shows the main functions.
- First one can navigate to the tab `Tickets Market` where one can buy tickets.
    - Just select a plan, enter your name, and purchase* a ticket.
    - A ticket has an expiry date and limited number of usages.
        This allows you to use a ticket a finite number of time in a specific date range.
- You can then visit the `Tickets Wallet` tab, there you can view all your tickets.
- If you are a conductor**, you can visit the `Rides Checking` tab.
    - Here you can see all the rides for this week that haven't been checked and that can be checked in the future.
    - Note that only the rides in the next hour can be checked.
    - For this purpose, use the time travel functionality at the top.
    - The schedule for the weekly rides can be found below.
- When a ride is chosen to be checked, a form appears at the top.
    - Here you can choose to cancel checking (using the `x` button).
    - You can select which conductor are you.
    - Tickets can be added to be checked in the following way.
        - Add a name in the name field, then click `list tickets`.
        - Choose the ticket from the dropdown, and click the `+` button.
        - The ticket should appear in the list above.
        - A specific ticket checking can be canceled using the `x` button.
    - Then the `Check Ride` button should be clicked to trigger the checking.
- Here the backend started verifying, the flow is explained in the following sections.

#### Notes
- `*`: You don't actually pay for a ticket, in the future, here one would show a form to accept payments but that was out of the scope for this project.
- `**`: As this is only a simulation, you are both the conductor, and the customer.
    Though here in the future one should provide authentication and authorization so conductors and customers have separate and limited functionalities.

### Schedule
|       | MONDAY                 | TUESDAY               | WEDNESDAY             | THURSDAY               | FRIDAY                | SATURDAY              | SUNDAY                |
|-------|------------------------|-----------------------|-----------------------|------------------------|-----------------------|-----------------------|-----------------------|
| 9:00  | city mall - rekord (2) |                       |                       |                        |                       |                       |                       |
| 9:15  |                        |                       |                       |                        |                       |                       |                       |
| 9:30  |                        |                       |                       |                        |                       |                       |                       |
| 9:45  |                        |                       |                       |                        |                       |                       |                       |
| 10:00 |                        |                       |                       |                        |                       |                       |                       |
| 10:15 |                        |                       |                       |                        |                       |                       |                       |
| 10:30 | rekord - gradezen (1)  |                       | rekord - gradezen (1) |                        | rekord - gradezen (1) |                       |                       |
| 10:45 |                        |                       |                       |                        |                       |                       |                       |
| 14:00 |                        |                       |                       |                        |                       |                       |                       |
| 14:15 | finki - city mall (3)  |                       |                       |                        |                       |                       |                       |
| 14:30 |                        |                       |                       |                        |                       |                       |                       |
| 14:45 |                        |                       |                       |                        |                       |                       |                       |
| 15:00 |                        |                       |                       |                        |                       | rekord - gradezen (1) | rekord - gradezen (1) |
| 15:15 |                        | finki - city mall (3) |                       |                        |                       |                       |                       |
| 15:30 |                        |                       |                       |                        |                       |                       |                       |
| 15:45 |                        |                       |                       |                        |                       |                       |                       |
| 16:00 |                        |                       |                       |                        |                       |                       |                       |
| 16:15 |                        |                       | finki - city mall (3) |                        |                       |                       |                       |
| 16:30 |                        |                       |                       |                        |                       |                       |                       |
| 16:45 |                        |                       |                       |                        |                       |                       |                       |
| 17:00 |                        |                       |                       |                        |                       |                       |                       |
| 17:15 |                        |                       |                       | finki - city mall (3)  |                       |                       |                       |
| 17:30 |                        |                       |                       |                        |                       |                       |                       |
| 17:45 |                        |                       |                       |                        |                       |                       |                       |
| 18:00 |                        |                       |                       |                        |                       |                       |                       |
| 18:15 |                        |                       |                       |                        | finki - city mall (3) |                       |                       |
| 18:30 |                        |                       |                       |                        |                       |                       |                       |
| 18:45 |                        |                       |                       |                        |                       |                       |                       |
| 21:00 |                        |                       |                       | city mall - rekord (2) |                       |                       |                       |
| 21:15 |                        |                       |                       |                        |                       |                       |                       |
| 21:30 |                        |                       |                       |                        |                       |                       |                       |
| 21:45 |                        |                       |                       |                        |                       |                       |                       |

## Flow
### Communication between modules
This is done using RabbitMQ.
- `shared-kernel` takes care of creating the queues.
- In `shared-kernel/event` we define the events types.
    - For a seamless usage, the types are grouped according to the task.
- In `shared-kernel/event/sender` we define senders which are used by the other modules.
    - This is done so other modules don't even know what kind of internal communication is used.
    - They are with normal types and receive normal types, all they know is that the classes send events, but don't know how.
    - For this purpose, some modifications are done, since `List`s can't be serialized to be sent over RabbitMQ,
        neither can the external class `Option` from the [Arrow](https://arrow-kt.io/) library.
        - The event types sometimes offer a different serializable version, those types offer a convert method to convert back and forth.
        - This way we send the serializable version over RabbitMQ but use the "fancy" version anywhere else.
        - This is handled by the senders and the handlers (will come back to these in a minute).
    - Senders always expect an answer, even an empty one, this is done so we achieve synchronous communication.
- The handlers exist in each module's `application` package usually, it is usually named _`SomeThing`_`EventHandler`,
    they always communicate with a service in the same package so the module itself can use that service, if needed, decoupled.
    - The handlers also respect those modifications that were mentioned earlier by calling `convert` and other methods when needed.

### Time
- Whenever any of the modules need to work with the current time, they call the time management module.
- Time is always `Instant`, so whenever we need to work a more helpful format, we convert it to `ZonedDateTime` at the `UTC` timezone.

### Tickets
#### Purchasing
Here the flow is pretty straight forward:
- Predefined plans can be fetched which then are passed in the purchasing form.
- Purchasing tickets requires a form which consists of the plan and the customer name.
    - The customer name should be 0..5 words only.
    - The plan should be one of the predefined plans.
#### Listing
Checking a customer's tickets is also supported, they are searched using the customer's name.

### Rides
#### Listing
- Rides are shown for the next week only. A week starts on Monday and ends on Sunday.
- Since each ride has multiple occurrences in a week, those are flattened to create a `RideDTO`.
    - A `RideDTO` is a `Ride` but with the occurrence being a single `TimePoint` (so a single `Ride` gets flattened into many `RideDTO`s).
    - This is done since they need to be sorted.
- The flattened rides are then filtered such that any rides from this week in the past don't appear.
    - So if today is Tuesday, rides from Monday won't appear.
- Then the flattened rides are sorted by occurrence `TimePoint`.

#### Checking
- Here a conductor must supply a form which contains:
    - His (the conductor's) ID.
    - The ride's ID, the one that is being checked.
    - A list of ticket IDs that boarded that tram.
- First each of these IDs is verified by calling different modules.
- Then it is confirmed that the ride hasn't been checked for that timeslot.
    - This is done by checking whether the same ride has been marked as completed (in the `CompletedRide` table) in the past 60 minutes.
- After that it checked whether that ride can actually be checked at that time (so we don't check too early or too late).
    - The rule is, max 60 minutes early, and max 15 minutes late.
- Then we check if all the tickets are valid.
    - First of all, no 2 tickets must belong to the same customer.
    - Tickets can only be checked before their expiry dates.
    - Tickets must have usages left in them.
- If all these previous rules apply and pass, then we can proceed.
    - We check every ticket, that is, we reduce the left usages by 1.
    - We check the ride, that is, we add it to the `CompletedRide` table.
