# Vulnerability Analysis

## A1:2017 Injection

### Description
Het invoeren van code dat niet is toegestaan 
door een hacker.

### Risk
Er is risico dat als er een guess of id van de game 
wordt ingevoerd deze vervangen kan worden 
door schadelijke code

### Counter-measures
voor het gameid wordt er in de Spring framework 
gebruik gemaakt van prepared statements.  
Voor de guess zou er gebruik gemaakt kunnen worden van
het checken of er coden wordt ingevoerd in plaats van een guess.

## A8:2017 Insecure Deserialization

### Description
Het direct terug geven van data zonder het aan te passen.

### Risk
Dat de applicatie direct java objecten terug geeft. 

### Counter-measures
Spring zet het object standaart om in JSON.

## A9:2017 Using Components with Known Vulnerabilities

### Description
Een hacker kan gebruik maken van een bestaande exploit 
in een gebruikte framework, libraries of add on. Dit kan zeker 
een probleem zijn als het een oudere variant is die gebruikt word
of niet meer ondersteund / bijgewerkt wordt.

### Risk
Er is het risico dat de gebruikte frameworks, libraries of add on 
bestaande of bekende exploits hebben, omdat ze niet worden 
geupdate als er een nieuwe versie van is.

### Counter-measures
Het regelmatige updaten van de dependency door de hulp van dependabot.com.
