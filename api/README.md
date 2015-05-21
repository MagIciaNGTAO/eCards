
This module contains the core API for sending emails (greetings or otherwise).
It supports sending emails through a primary email service provider and in the
even failure through a backup email service provider.

The API uses HK2 Dependency Injection Framework to create, wire, and manage
services. In addition it utilizes HK2 RunLevel feature to manage application
state.

The basic principle behind run level service is that certain services (i.e.
backup services) can become available on-demand or in the even of system state
change (i.e. exception or service failure). For example, when the primary email
service provider fails the application transitions to "failover" and thus
enabling the backup email service provider.

## API Overview

### Common
The common package contains common classes used throughout the code.

### Config
The config package contains application configuration related classes.

### Email
The email package contains email service api and supporting classes. Two
implementations of email services are provided, Mailgun and Mandrill.

### JavaMail
The javamail package contains java mail related factory provider classes.

### Kernal
The kernal package contains classes for managing and reporting the state of
application. ServiceKernal class is where the magic of transitioning the
application to a different state managed. When the application starts users
need to instantiate ServiceKernal service to order to transition the application
to startup state (implicitly done during service creation). In the event of a
ServiceKernalException type exception users should call the ServiceKernal.failover()
to brining up and switch to backup services.

### Template
The template package contains api to to load and interpolate email templates
from various sources. Currently only the loading of YAML based email templates
from the class path is supported.

Note:
HK2 currently isn't able to handle automatic failover service location so we
have to manage which email service is used our selves through
EmailServiceProvider class. I will talk to John Wells (maintainer of HK2) about
supporting automatic failover handling.


