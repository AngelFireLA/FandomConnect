# FandomConnect
 FandomConnect, the minecraft plugin that connects your server and your fandom
 
 
## Concept
 FandomConnect is a plugin that will send information from your server, to your configured fandom.
 It Supports Player Profiles from RoleplayCard : https://github.com/LaBoulangerie/RolePlayCardPlugin
 It supports Towny : Towns and Nations (and some more info on player profile), we will see how in configuration part.


## Setup
Get the Jar from releases or build it yourself !
Then put it in your plugins folder and then start the server.
First time, it will have an error, but it's because config.yml isn't configured yet.
So next step, configure the plugin.
Restart/reload your server and enjoy !


## Configuration
First, input your fandom link :
```links:
    fandom: "https://**yourfandom**.fandom.com"```

Now to the login phase, to create pages on the fandom, you will need a fandom bot account
How to get a bot password you may ask, go check on this page : https://community.fandom.com/wiki/Help:Bots at the Using Special:BotPasswords section

Next part, choose what do you want to send to your fandom :
using the RoleplayCard integration :
```    rpcard:
        integration_enabled: true
        create_page_to_list_players: true
        create_infobox_for_players: true```
You can select if you want it enabled, if you want to create a page that will list every player page crated
And if you want the plugin to create a default infobox template to support your player information (only disabled if you want to use your own one)

Now the Towny Integration :
`        integration_enabled: true` First select if you want it enabled 
```        towns: true
        create_page_to_list_towns: true
        create_infobox_for_towns: false
        
        nations: true
        create_page_to_list_nations: true
        create_infobox_for_nations: false```
You can choose if you want only towns or only nations to have their data sent.
Like the RoleplayCard integration, you have the option to create a page that will list all towns/nations pages. You can also choose if you want to use the default infobox or your own custom one.

RoleplayCard supports 2 language : French and English, so there is 2 language files where you can change the messages
`language: en_US`

## Infoboxes (work in progress)
Data from your players/towns/nations must be sent into infoboxes, otherwise it would be just a bunch of unordered wikicode (fandom's language), so I will teach you how to use your own infobox :
```infoboxes:
    players: "default-players.txt"```
A "infoboxes" folder will be created where you will need to put all(I mean really ALL) the wikicode of your infobox in a text file, this is where you put the name of your textfile (or you can edit the default one).
Your infobox code will need to have some changes :
you will have to edit the, data source | params | exemple usage, (they are the same things), of your infobox to include several words :
For Player Infoboxes:
- title1 (this will be the name of the player)
- rp_name (the roleplay name of the character)
- age (the age of the character)
- dead_or_alive (if the character is dead or alive)
- titles (if the character have titles like, for example, "The Great")
- religion (the religion of the character)
- jobs (the job(s) of the character)
- race (the race of the character)
- money (towny integration : money of the player)
- town (towny integration : town of the player)
- nation (towny integration : nation of the player)
For Town Infoboxes:
For Nations Infoboxes:
Once your infobox text file is ready, save it and your infobix is ready
