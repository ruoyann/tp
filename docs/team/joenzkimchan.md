---
layout: page
title: Kim-Chan Tze Yong Joenz's Project Portfolio Page
---

### Project: StudyTracker

StudyTracker is a desktop application for users to keep track of their favourite study spots.  The user interacts with it using a CLI, and it has a GUI created with JavaFX. This project was built upon 
AddressBook - Level 3. It is written in Java, and the team has contributed about 12 kLoC.

Given below are my contributions to the project.

- **New feature**:  Added the functionality to add study hours to each study spot.
    - What it does: each study spot now contains a field which stores the number of hours that the user has studied at the location. 
    The user can use the `log` command to further add hours to this number, reset this number or reset the hours at all stored study spots.
    - Justification: This product gives users a reason to come back to the application, to keep tabs on how long they study at a certain 
    location. Some users may feel a sense of accomplishment when they study longer at certain spots, also giving them more reason to keep 
      track of how long they study.
- **New feature**: Added Pie Chart that keeps track of the top 5 studied locations.
    - What it does: A pie chart can be seen in our GUI. This pie chart keeps track of the studied hours of the top 5 locations. 
    It changes dynamically as the user logs hours at locations.
    - Justification: It can be nice for users to see which are the top 5 locations at which they study at. It also gives user a sense of 
    accomplishment when they log hours and they can see the bar in the pie chart increase.
    - Credits: Credit to Jeff in the team, who did the styling for the Pie chart. I worked on the logic behind the pie chart, while he worked on the 
    design of it
 - **New feature**: Added quick access to commands in the Help window, which originally only contained a link to the user guide.
    - What it does: Adds a summary window in the help window, which allows users to easily see the commands and its command syntax.
    - Justification: The help window used to only contain a button which copied the link to the user guide. I felt that this was not very useful, 
    and would have preferred if it contained a summary to all the commands.
    - Credits: Jeff, who once again did the styling for the Help window.
  

 - **Enhancements to existing features**:
    - Refactored existing AB-3 code to fit StudyTracker. This was at the beginning of the project, since a lot of the class names did not match 
    StudyTracker's needs.
    - Changed `add` command to only need to take in a name and a rating. Changed `edit` command to use name instead of index.
   

 - **Code contributed**: <a href="https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=-CS2103T-T09-1%2F&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17">Reposense link<a/>
   

 - **Documentation**: 
    - User Guide:
      - Added documentation for log command
      - Updated User Guide to be more user-centric, and added more tables to make it clearer
    - Developer Guide:
      - Updated some outdated diagrams and added in the implementation for log command
  
 - **Community**:
    - PRs reviewed with non-trivial comments: [#22](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/22), [#48](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/48),
      [#65](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/65), [#70](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/70), 
      [#73](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/73), [#115](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/115), 
      [#123](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/123),  [#125](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/125)  
    

