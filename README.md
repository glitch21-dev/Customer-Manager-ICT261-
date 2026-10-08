# Customer Manager

ICT261 Advanced Java Programming - LAB 3 (class presentation)

**Name:** Evidence Chisenga
**Student number:** 202308123

## About

A small JavaFX app for entering customers and viewing them in a table.
Data is stored in memory only, so closing the app clears it.

## Features

- Form with a customer name field and a province list
- Input validation with clear messages (missing name, missing province)
- Customers shown in a TableView (name and province columns)
- Delete with a confirmation dialog (Delete or Cancel)
- Keyboard support: Tab to move between controls, Enter to save
- Dark (Aurora) theme styled with CSS

## Tech stack

Java 17+, JavaFX 21, Maven

## Project structure

| File | Role |
| --- | --- |
| `App.java` | View: builds the window |
| `CustomerController.java` | Controller: handles actions, validates, gives feedback |
| `CustomerService.java` | Service: application rules and the ObservableList |
| `Customer.java` | Model: one customer |
| `style.css` | Appearance |

## How to run

From the project root (the folder containing `pom.xml`):

```
mvn clean javafx:run
```

## Screenshots

Lab evidence is in the `screenshots/` folder:
valid save, missing-name error, missing-province error, delete cancellation, confirmed deletion.
