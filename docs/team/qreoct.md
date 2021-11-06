---
layout: page
title: Jefferson Lim's Project Portfolio Page
---

### Project: StudyTracker
StudyTracker is a desktop application for students to keep track of their favourite study spots.  The user interacts with it using a CLI, and it has a GUI created with JavaFX.
This project was built upon [AddressBook - Level 3](https://se-education.org/addressbook-level3/).
It is written in Java, and the team has contributed about 12 kLoC.

StudyTracker was built to solve the problem of not finding productive study spots.
We aim to maximise students’ productivity by making it easy to find the best place to study.

### Feature contributions

* [**Alias feature**](https://ay2122s1-cs2103t-t09-1.github.io/tp/DeveloperGuide.html#alias-feature):
  Added an Alias Command for user-defined commands (PRs [#65](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/65), [#103](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/103))
  * **What it does**: allows the user to set custom commands to reduce typing
  * **Justification**: aliases improves StudyTracker significantly because a user can customize the program to their own needs, and complements other features (e.g. logging, editing) seamlessly
  * **Design considerations**: designing this feature required overhauling logic to parse user input, in order to maintain a list of commands in addition to user aliases
  * **Design considerations**: I also considered whether aliases should simply map a shortcut to a single command word, or allow aliases to map to other aliases (like in Unix shell).
    [Ultimately, a middle option between the two was chosen](https://ay2122s1-cs2103t-t09-1.github.io/tp/DeveloperGuide.html#design-considerations).


* **Colour themes & GUI enhancements**: Designed and implemented the GUI of StudyTracker in JavaFX.
  Also implemented [**custom colour themes**](https://ay2122s1-cs2103t-t09-1.github.io/tp/DeveloperGuide.html#themes) using CSS to change the look of StudyTracker, along with a Style Guide for developers and designers.
  (PRs [#85](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/85), [#92](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/92), [#110](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/110), [#131](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/131))
  * **What it does**: users access a Settings menu to select the colourscheme of their choice.
    With the [Style Guide](https://ay2122s1-cs2103t-t09-1.github.io/tp/DeveloperGuide.html#themes-style-guide), developers can further enhance and create new themes of their own.
  * Created mockups of GUI using Figma
  * **Credits**: Team members [Joenz](joenzkimchan.md), who wrote the logic for `Help Window` and `Hours Studied Pie Chart`,
    and [Danqi](limdanqi.md), who helped with the GUI design

![GUI](../images/UiThemes.png)

### Other contributions

* **Test code**
  * Wrote JUnit Tests for existing and new features (1.2k+ LOC for tests):
    [#48](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/48),
    [#65](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/65),
    [#231](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/221)
  * 100% test coverage for `Alias` feature


* **Documentation**
  * Documentation was written with the intent to be clear and user-centric.
    When technical depth was required, I aimed to be concise and understandable.
  * **User Guide:**
    * Cosmetic tweaks to existing documentation & README, with a focus on brand voice and user-centricness:
      [#102](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/102),
      [#144](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/144),
      [README](https://github.com/AY2122S1-CS2103T-T09-1/tp#readme)
    * Updated and standardized diagrams, and created annotated screenshots of StudyTracker: [#155](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/155)
  * **Developer Guide:**
    * Added thorough implementation details with accompanying PlantUML diagrams, documenting the `alias` command and Themes feature: [#96](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/96), [#110](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/110)


* **Contributions to team-based tasks**
  * Refactored existing [AB-3](https://se-education.org/addressbook-level3/) code to fit StudyTracker.
    This was at the beginning of the project, since a lot of the class names did not match StudyTracker's needs:
    [#22](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/22), [#48](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/48).
  * Facilitated team meetings :relaxed:


* **Non-trivial code reviews**
  * [#50](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/50) Improving code logic
  * [#81](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/81) Suggestions for code quality
  * [#90](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/90) Suggestions for coding standards
  * [#97](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/97) Identified potential UX and consistency issues


* My overall code contribution can be found at [this RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=qreoct)
