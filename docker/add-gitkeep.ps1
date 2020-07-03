Get-ChildItem -Directory -Recurse |
        Where-Object { $_.GetFileSystemInfos().Count -eq 0 } |
        ForEach-Object { New-Item -Path $_.FullName -Name ".gitkeep" }
