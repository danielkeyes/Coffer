# Coffer

***Definition***: a strongbox or small chest for holding valuables

Also an excuse to create a pin page app using some different technologies for practice 

## Practice Technologies

- Compose
- Compose navigation
- Hilt
- Preferences Datastore

## Security

For password security we generate a random salt then hash and salt the password and store the salt and hash result. 

To reverse engineer this would require a person to get the salt, and then use that to generate all hashes to get the password, which is over the top and probably not gonna happen

## TODO

- make pin page more reusable
- change pin option after pin logon 
- make reusable scaffold
- setup flow step 1 text on what happens after setting pin with acknowledge button
- update colors
- add text to content screen about how to encrypt items?