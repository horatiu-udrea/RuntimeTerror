### Conference Management System Documentation
### RuntimeTerror 927/2

---

# Requirements

The objective of the Conference Management System is to automate the management of information for a scientific conference and simplify the processes related to it.  

These include, but are not limited to:
- **Publicly displaying the conference information in an accessible manner**, like the conference name, interval, program committee members, details of the sections and papers that are going to be presented
- **Managing the submission of papers by authors**, with support for uploading `.pdf` or `.docx` files and specifying metadata and multiple authors
- **Reviewing of the papers by the chosen program committee**, with the option for them to bid on the papers before they are assigned to review them
- **Refining the paper as an author taking into account the received reviews** and also uploading a different `.pptx` file for the actual presentation
- **Changing the public conference details as a Steering Committee member** and managing its sections

# Stages of implementing the application

### 1. Requirements elicitation
- Getting familiar with the requirement and the new team
- Creating the **Use Case Diagram**
- Writing the [Software Requirements Specification](SRS.md) document
### 2. Analysis
- Creating the **Class Diagram**
- Creating the **Sequence Diagrams**
### 3. System design
- Choosing the implementation **languages** and **frameworks**
- Creating the **Architecture Diagram**
### 4. Object design
- Refining the **Class diagram**
- Creating the **Database Diagram**
- Creating **wireframes** for the UI
### 5. Implementation
- Implementing the **database**
- Implementing the **ORM**
- Implementing the **HTTP API**
- Implementing the **Controllers**
- Implementing the **UI**
### 6. Testing
- **Integration testing** with API calls
- **System testing** using the UI

# Tools and technologies used

- **Diagrams:** [Visual Paradigm](https://www.visual-paradigm.com/), [draw.io](http://draw.io)
- **Frontend:**
    - Languages: [HTML](https://en.wikipedia.org/wiki/HTML), 
    [CSS](https://ro.wikipedia.org/wiki/Cascading_Style_Sheets), 
    [JavaScript](https://ro.wikipedia.org/wiki/JavaScript), 
    [jQuery](https://jquery.com/)
    - Tools: [Node.js](https://nodejs.org/), [Webpack](https://webpack.js.org/)
    - Editor: [Visual Studio Code](https://code.visualstudio.com/)
- **Backend:**  
    - Language: [Kotlin](https://kotlinlang.org/)
    - ORM: [Exposed](https://github.com/JetBrains/Exposed)
    - Server: [Ktor](https://ktor.io/)
    - Editor: [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- **Database:** [MySQL](https://www.mysql.com/), [DataGrip](https://www.jetbrains.com/datagrip/)
- **Others:** [Trello](https://trello.com/), [Git](https://git-scm.com/), [GitHub](https://github.com/)

# Diagrams
_See [images](images)._

# Help
_See [manual](Manual.md)._