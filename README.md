# Xapo test project
This is a project used to evaluate candidate's coding skills and Android knowledge.

## Description
The project's idea is to list trending projects from Github and then tap on one of them and show their details.

**Requirements**
- Write your application in Kotlin
- Ensure your application looks good on different screen sizes and densities
- Ensure your application supports Android API 23+
- Use good architecture and design patterns
- Use valuable external libraries that you are used to
- Use reactive programming

**Bonus** 
- Filtering and ordering functionalities
- Unit tests
- Shared element transition or other animations (but only if they are functional to the user experience)

If there is something not specified, please be free to decide on it. 
Let us know if you need something or you have any doubt about the project.

## Jordi Coll Marin - Test

**General info**
Github Portal is an App that will help users to find repositories around the Github platform. The App will show a list of repositories filtering by the programming language the user has selected: Kotlin, Swift, Javascript or Go.

**Architecture**
Github Portal is built using clean architecture following the separation of concerns principle to make it more testable and maintainable. MVVM is the selected architecture for the presentation layer.

**Features**
- Get a list of 20 repositories. By default Kotlin programming language is selected and results are ordered according repositories stars.
- Filter by different programming languages: Kotlin, Swift, Javascript and Go.
- Sort results by number of stars, number of forks, number of help-wanted issues and by update date.
- See details of each repository: name, description, url, programming language, number of stars, license, owner name and owner avatar.

**Future**
- Use Paging library from Jetpack libraries.
- Move from MVVM to MVI to manage view states.
- Add more unit tests.