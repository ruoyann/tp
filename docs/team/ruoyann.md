---
layout: page
title: Yin Ruoyan's Project Portfolio Page
---

### Project: StudyTracker

StudyTracker is a desktop application for users to keep track of their favourite study spots.  The user interacts with it using a CLI, and it has a GUI created with JavaFX. This project was built upon
AddressBook - Level 3. It is written in Java, and the team has contributed about 12 kLoC.

Given below are my contributions to the project.

* **New Feature**: Amenity feature ([charger](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/58), [food](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/73), [aircon](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/76)) together 
  with 
  Danqi
    * What it does: allows the user to add amenities such as wifi, charger, food and aircon to the Study Spots
    * Justification: This feature allows users to filter and list only Study Spots with certain amenities to
      specifically search for Study Spots with amenities they need.

* **Enhanced Feature**: [List command](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/154)
    * What it does: Allow users to list and filter study spots based on specific amenities and/or ratings.
    * Justification: This feature originally allows users to list only based on favourites and tags, however I felt that
      the functionality was not enough for users as they may want to only list study spots that are rated 5/5 or
      only study spots that have food if they are feeling hungry.
      
* **Enhanced Feature**: [Delete feature](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/70)
    * What it does: allows the user to delete Study Spots based using the Study Spots' names, rather than their 
      indexes as per the original AB3 
    * Justification: This feature is enhanced to be consistent with all our other commands that only uses the Study 
      Spots' names rather than their indexes. It is also more intuitive to delete based on names rather than 
      scrolling through the list to find out which index matches to which study spot since names are usually short 
      and easy to remember.

* **Enhanced Feature**: [Command outputs](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/123)
    * What it does: Make the outputs displayed to the user more readable and comprehensive across multiple commands
    * Justification: This feature is enhanced so that just the right amount of relevant information is displayed to the
      users in a user-friendly format so that they are not overwhelmed with information.
      
* **Enhanced Feature**: [Help feature](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/39)
    * What it does: allows the user to visit our User Guide by clicking on a button that opens the link in browser,
      as compared to the previous version of copying the link to clipboard.
    * Justification: This feature improves the convenience of opening our User Guide.

* **Testing**: All the above enhanced/added features, [Log Commands](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/125)
  
* **Bug-Fixes**: For bugs identified during PE-D 
    * Display correct error message for [tags with spaces](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/187) 
    * [Display correct error message](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/183) for invalid log commands 
    * Tweak User Guide to [fix discrepancies](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/189)  
    * [Standardise case-insensitive names](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/198) across all commands
    * [Remove StudySpot from favourite list](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/207) if it's deleted before user un-favourites study spot

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=ruoyann&tabRepo=AY2122S1-CS2103T-T09-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Documentation**:
    * User Guide:
        * Change from passive to [active voice](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/128) throughout 
          the User Guide
        * Add visuals to User Guide to enhance users' understanding
        * Tweak User Guide according to new or enhanced features 
    * Developer Guide:
        * Edited the implementation details and [diagram](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/100) of the `delete` feature.
        * Edited the details of `model` [diagram](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/117). 
        * Added a [sequence diagram](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/120) for the `amenity` feature.

* **Community**:
    * Reported [bugs and suggestions](https://github.com/ruoyann/ped/issues) for other teams in the class (examples: [1](https://github.
      com/ruoyann/ped/issues/3), [2](https://github.com/ruoyann/ped/issues/5), [3](https://github.com/ruoyann/ped/issues/7))
      
