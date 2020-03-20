# 1. Introduction 
The Conference Management System is a web-based application which aims to help people organize scientific conferences faster and quicker
## 1.1 Purpose
The purpose of the product is to ease the hosting of scientific/academic conferences centered around the presentation of papers/projects.
## 1.2 Intended Audience
 - Primarily: The Conference Management System is addressed towards the academic and scientific comunities.
 - Secondarily: The Conference Management System can be used by any individual seeking a platform to host a similar conference.
## 1.3 Intended Use
 The Conference Management System is to be used strictly to organize scientific/academic conferences.
 **(pe perforce era recomandat ca field-ul asta sa contina intended use pentru SRS. also intended use pentru product = prupose)**
 The Document is to be used as follows:
  - By Developers:
    - As a general framework for the application.
  - By QR:
    - As a general description meant to assist with creating tests.
## 1.4 Scope 
- Objective: Support the automatic management of information related to scientific conferences
- Benefits:
  - Gather information about speakers and papers easier
  - Assign papers to reviewers based on preferences
  - Create an accurate timetable for the meeting
  - Assign the most appropriate rooms to the more requested sections of the conference
- Goals:
  - Create an easy-to-use application to automate all the processes involved in the organization of a scientific conference
  
# 2. Overall Description

## 2.1 User Needs

## 2.2 Assumptions and Dependencies
It is assumed that:
- the user is familiar with an internet browser and also familiar with handling the keyboard and mouse
- the user has access to an internet browser since the application is a web-based application
- the user possesses decent internet connectivity
- the user possesses a valid email address

# 3. System Features and Requirements

## 3.1 Functional Requirements
The system must:
- provide the user with a means of creating an account and authenticating on the platform with their credentials (username/password)
- let the authenticated user perform the specific actions required for each phase of the conference (for any conference)
- change phases of the conference according to chosen deadlines
- present its public information to the users

## 3.2 External Interface Requirements
 There are 4 types of external interface requirements: User Interfaces, Hardware Interfaces, Software Interfaces and Communication Interfaces.
 #### User Interfaces : 
 The conference management system contains a form for users to log in, register, choose a section or buy a ticket. 
 The users can be parts of the following categories: steering committe, program committee memeber or authors.
 Three will be forms for the steering committee to create a conference or to create a conference section.
 Also, there will be a specific submision form to upload a paper, to improve or to upload contents of a presentation.
 The web application will contain a platform to review papers,change them in case of conflicts and to emit final decision for conflicting papers.
 As part of the system, it will be build a dashboard to change conference deadlines, assign papers for reviewers and request decision for a conflicting paper.
 As part of the users communications it will contain a dashboard and a platform for users to have the chance to bid on papers.
 #### Hardware Interfaces :
 The Conference Management System uses hardware interfaces similar to most online web applications including visual and interactional support (e.g monitor, keyboard, mouse).
 #### Software Interfaces :
 The system will include a database to store the user information, conference information and other types information  specific to the account type. The system will be separate from the native operating system as it functions entirely as a web application displayed within a web browser (i.e. Chrome, Firefox,  Internet Explorer).  
 #### Communication Interfaces :
 The system is a web application, so it needs to communicate with all web browsers available.The system also needs to communicate with an e-mail service to send reminders to  users. The system will use HTTP communication standards. 
## 3.3 System Features
The system needs to be:
- able to logically behave as three seperate systems (Backend, Frontend, Database)
- able to run the website responsively(Should work on multiple devices and browsers)
- able to distinguish between different users when accessing the database
- **not sure** able to operate transparently on data that is spread across a variety of different databases
## 3.4 Nonfunctional Requirements
- Reliability: The system should be reliable even when a lot of users access it at the same time
- Data integrity: The stored data should not become corrupted.
- Quality: The system should be polished.
- Maintainability: The system should be easily maintained.
