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
