## Rest Module
This module contains a simple Jersey/Grizzly based REST application to send out
an email greeting.


## Building the Application
To build the application run:
> mvn install

Once this module is built it generates a zip file in the target directory that
contains the rest application along with configuration files, email templates,
and scripts.

## Running the Application
To run the application perform the following:

1. Unpack the zip file in "target/rest-x.x.x.bin.tar.gz"
2. Change directory to the bin director
3. Run the start script:
 > ./start.sh

## Calling the Greeting REST Service
Once the application starts make sure the application WADL is accessible by
going to the following url:
> http://localhost:1979/api/application.wadl

To send a holidays greeting use curl to post a JSON entity of the user you wish
to send greetings to:
> curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"First Name", "lastName":"Last Name", "email": "local.part@domain.part"}' http://localhost:1979/api/greetings/holidays

To send a birthday greeting use curl to post a JSON entity of the user you wish
to send greetings to:
> curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"First Name", "lastName":"Last Name", "email": "local.part@domain.part"}' http://localhost:1979/api/greetings/birthday

Note that the json payload is validate. The entity must have a non-empty first
and last name, and the email must be a valid email address. In addition the
content of first/last name and email must be safe html and don't contain
malicious code, such as embedded <script> elements.
