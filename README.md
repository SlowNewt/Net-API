
# Net-API (UNFINISHED)

Net-API is a **Minecraft** plugin made to sync Minecraft servers using **MySQL**.
## Information

#### Is this project finished?

- Simple answer: No!
- It dose work but its un-optimized And some things may not work.

#### What do i need to use this plugin?

- An SQL database.
- A **1.8.x** Minecraft server

#### What version dose this plugin support?

- This plugin has only been tested on **1.8.8**, but *should* work on versions up to **1.12**
- I will be adding more tested versions soon.
## Used By

This plugin is currently used by:
- [SnowMC](https://discord.snowmc.net)
- [SlowNewt Events](https://discord.slownewt.net)


## Commands


- /admin
```
shows a help message.
```
- /admin serverinfo <server>
```
Displays info about a server like TPS, Player Count, MOTD, Base Version, And IP / Port.
```
- /admin cmd <server/all> <cmd>
```
Runs the spesified command on the spesified server.
```
- /admin list
```
Lists all the servers and there info in a GUI.
```
- /admin sql
```
Shows SQL information like the Host, Username, and Password.
```
- /admin forceupdate
```
Updates all SQL information to the database.
```
## Installation

To install this plugin

```bash
Stop your Minecraft server.
Add the Net-API.jar file in to your 'plugins' folder
Start your Minecraft server.
Add your SQL database in the Config.yml *do not touch the 'server-id' section.*
Restart your Minecraft server.

Do this for all the servers you want to connect!
```


## Roadmap

- Additional version support.
- Add more integrations with difrent plugin stats.
- Make the plugin more customizable.
- optmize the plugin.
- Add options to use MongoDB, and Redis!
