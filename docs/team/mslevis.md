---
layout: page
title: Owen Tan's Project Portfolio Page
---

### Project: StudyTracker

StudyTracker is a desktop application for users to keep track of their favourite study spots. The user interacts 
with it using a CLI, and it has a GUI created with JavaFX. This project was built upon
AddressBook - Level 3. It is written in Java, and the team has contributed about 12 kLoC.

Given below are my contributions to the project.

* **New Feature**: Adding study spots to favourites.
  * What it does: allows the user to add study spots to favourites.
  * Justification: This feature provides a convenient way for users to track their favourite study spots and can be used
    together with list command to view favourite study spots with ease.
  * Highlights: This enhancement paved the way for subsequent features such as viewing favourites using list command as well as the favourites panel in the GUI. It required an in-depth knowledge of Logic, Model and Storage component. Various commands could modify study spots and favourites list in Model needs to be updated accordingly. The implementation too was challenging as it required updating the existing commands.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.
  * What it does: allows user to navigate to previous commands
  * Justification: Command history helps to save time for the user by getting the previous command with an up arrow and changing only what is necessary. This is useful when typing similar commands consecutively or simply retrieving commands typed a while ago. This is definitely a quality of life upgrade for users in a CLI application.
  * Credits: Jeff first wrote an initial version that stores only the most recent command that is retrieved with up key. 
    I improved on it such that you can cycle through the command history with up/down keys. The gist of it was adapted from [here](https://stackoverflow.com/questions/41604430/implement-command-history-within-java-program). I modified it so that the cycling through of command history was smoother. 
    
* **Enhanced Feature**: Enhanced list command to filter study spots.
  * What it does: allows the user to filter study spots by tags, favourites, etc.
  * Justification: This feature provides a convenient way for users to view study spots based on various properties with ease.
  * Highlights: The implementation of this feature was difficult as it involved flags, which were not present in AB3 initially.
  Thus, it required a strong knowledge of how commands are parsed in the Logic component. There were some design considerations
    when we allowed multiple flags to be used together. Ultimately, we decided to use an AND filter when having multiple flags.
  

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=T09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

* **Project management**:
  * Managed releases `v1.2.0` - `v1.3.1` (3 releases) on GitHub
  * Managed milestones on Github

* **Enhancements to existing features**:
  * Added a ResetAll to reset all study spot's studied hours

* **Documentation**:
  * User Guide:
    * Standardised the use of common symbols [\#124](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/124)
    * Rearranged the flow of contents in the guide [\#124](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/124)
    * Added simple logo to the guide [\#124](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/124)
    * Improve readability of command structure section [\#111](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/111) 
  * Developer Guide:
    * Update Logic section [\#109](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/109)
    * Added details of implementation of Enhanced List Command [\#109](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/109)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#183](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/183), [\#169](https://github.com/AY2122S1-CS2103T-T09-1/tp/pull/169)
