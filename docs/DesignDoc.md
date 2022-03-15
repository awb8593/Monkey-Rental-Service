---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: 5Guys
* Team members
  * Jack Yakubison jcy4561
  * Adrian Burgos awb8593
  * Eligh Ros edr5068
  * Trent Wesley taw8452
  * Jack Hester jrh339


## Executive Summary

This project's purpose is to act as an E-Store used for renting monkeys. It is a website in which users can search, sort, and rent monkeys for specified dates. It can also be used by an admin account to create new monkey listings, edit monkey listings, or delete monkey listings. The E-Store will offer a review feature in which users who have rented a monkey in the past can write a review for it that other users can see.

### Purpose
The main goal of this project was to provide both a front-end for an e-store focused on renting monkeys for parties and an API to handle inventory management and data persistence. Another goal of this project is to handle authentication for both customers and owners, as well as a working shopping cart for customers to store desired products in until they are ready to proceed with renting.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA | Single Page |


## Requirements

Our application will provide users with the ability to rent various
monkeys as well as allowing the owners to actively manage the site through
admin control. In this project, we will be required to develop controls for
the owners to utilize to effectively manage the e-store, such as as creating,
deleting, and updating the monkeys within the store. We will also be required
to effectively build a website from the front-end, developing product pages that
show the details of each monkey, a way to search through the list of monkeys by means
of a search bar and filters, and a shopping cart to checkout. In addition, we will
have to create a system for users and admins to log in, as well as a way for users to
post reviews of monkeys they previously rented, which will contribute to a rating system
for the monkeys.

### Definition of MVP
The minimum viable product for this project is a running website in which users have access to a variety of monkeys, which they can add to a shopping cart and request to rent. It should also have an admin account which has the ability to change the inventory on the website. Lastly, users should be able to write reviews for monkeys that they rented that can be accessed by other users.

### MVP Features
- Login Backend
- Login Page
- Owner Features
- Product Page
- Search for product
- Get a product
- Update a product
- Create a new product
- Delete a product
- Search bar
- Enable Filters
- Shopping Cart
- Rental Backend
- Post Review
- Read reviews

### Roadmap of Enhancements
Our biggest enhancement is the ability to rent a monkey for your event rather than simply purchasing one, which would be impractical to do through an e-store.

We plan to implement the ability to read customer reviews on the monkey you are currently viewing, as well as the ability to write reviews for monkeys who you have previously rented.


## Application Domain

This section describes the application domain.

![Domain Model](domain-model.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._



## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

When launching the e-store, the user will first be greeted to a login page where the user will
be prompted to login with their username or create a new account. Once the user has either logged in
or created an account, they will be redirected to the productlist page, where the list of monkeys
is displayed, as well as a search box to find specific monkeys. If the user is an admin, they can also 
edit and add monkeys while on this page via text boxes and buttons. When clicking on a monkey, the monkey's 
page will be displayed, showing information regarding the species, name, id, description, cost, and 
availability of the monkey, as well as an option to add it to the user's cart. When on the productlist 
page the user can be redirected to their cart by clicking on the cart icon and from their they will be 
taken to a page where they can remove monkeys from their cart as well as checkout.


### View Tier
> _Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side 
> to help illustrate the end-to-end flow._


### ViewModel Tier
> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._


### Model Tier

The Model Tier of our project communicates with the ViewModel Tier by receiving updates regarding user actions and requests, and in return sending it notifications relating to the back-end functions of the e-store and its inventory. It also houses our business logic and handles the system's data persistence. The logic is specifically concerned with managing the e-store's monkeys and users. The component concerned with product management gives the abilities of creating and removing monkeys from the inventory, as well as updating a monkey's specific details, such as one's species, description, and price. The user component provides the necessary abilities to create or manage specific users, manage the items in a user's shopping cart, and managing a user's list of currently rented monkeys.

### Static Code Analysis/Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
- 11 of stories that we finished pass all of their testing criteria
- 0 of the stories partially pass acceptance criteria
- 12 of the stories have yet to be tested
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
