# Paint & Chat Application
 
<!--
![Screenshot 2022-12-19 at 01 04 09](https://user-images.githubusercontent.com/103879144/208326576-51222721-80ad-47b7-8fc4-65950c7ff0c0.png)

</br>-->

![Screenshot 2022-12-19 at 00 47 46](https://user-images.githubusercontent.com/103879144/208325928-97f88ad4-1d05-4a67-80f3-6b1469656726.png)

</br>

![Screenshot 2022-12-19 at 00 43 27](https://user-images.githubusercontent.com/103879144/208325979-44b7fb36-ee0f-4837-b8b2-5ba4317e1d7d.png)

</br>

![Screenshot 2022-12-19 at 00 57 23](https://user-images.githubusercontent.com/103879144/208326313-cf5a0b02-894e-44e5-b286-307e418b946c.png)

</br> 

### Project structure

#### Introduktion

> Detta är en applikation med grafiskt gränssnitt skriven med JavaFX enligt MVC Model View Controller Koden har tester skrivna med Junit5. Det är ett ritprogram där användaren kan rita på en Canvas i JavaFX (GUI), och spara ritade objekt som SVG. Applikationen innehåller bland annat designmönster och nätverkskommunikation.

#### MVC

> Applikationen är implemeterad som en MVC applikation

![Screenshot 2023-03-02 at 20 57 53](https://user-images.githubusercontent.com/103879144/222538677-38c1cd5b-f2fd-4951-b98b-9f1b6dd1d145.png)

### Databindings och properties 

> Properties gör det bland annat möjligt att koppla ihop scenen så att vyn uppdateras automatiskt när man modifierar datan som finns bakom den. Properties är obervables som kan vara skrivbara eller endast läsbara. Varje property wrappar ett existerande Java objekt och adderar funktionalitet för lyssnande och bindningar.

### Canvas

Canvas iär en bild som kan ritas på genom att anvönda ett set av grafiska kommandon som erbjuds av en GraphicsContext. En canvas node kontrueras med bredd och höjd som specificerar storleken på en bild på vilken canvas ritkommandon renderas.

### Shapes

> Förutom fri ritning kan olika shapes ritas ut valfritt antal gånger i programmet. Nya shapes kan skapas med hjälp av en Factory / Builder pattern. och shapes som ritas ut representeras av en objekthierarki under programmets körning. Storlek och färg på shapes kan sättas på objektet som ska ritas ut. Utritning av nya shapes sker genom att klicka med musen där objektet ska ritas ut. (Centrum eller hörn). Redan utritade objekt kan väljas genom att gå över i select mode och klicka på skärmen. Musens koordinater används för att leta upp det objekt användaren klickat på. Med hjälp av en metod som frågar om koordinaterna är inom shapens area.
På den valda shapen kan man sedan ändra färg och storlek.

### Gränssnitt

> Gränssnittet för att välja inställningar, färg och shape görs med JavaFX Controllers så som Button, DropDown, Menu mfl.

### Export av ritade objekt (sparning)

> Export/filsparning av ritade objekt sker som svg-format vilket är ett vektorformat. Till detta används en FileDialog för att välja filnamn för sparning. Bilden kan sedan öppnas i en webbläsare. Svg är ett XML baserat vektorbildsformat för att definiera tvådimensionell grafik.

### Undo/redo

>Programmet ger möjlighet att ångra senaste ändringen man gjort. Ritar man tex en ny shape kan en
undo ta bort den senast ritade shapen och en redo kan återinföra ändringen man gjort. Applikationen innehåller en mer avancerad
lösning som implementerar Multilevel Undo och Redo genom knapptryckningar överst i fönstret implementerat med Command Pattern och Stack

### Tester

> Applikationen innehåller två Unit Tester med hjälp av JUnit5 mot controller / Model. Vilket innebär att varken JavaFX eller vyer blir inblandade när vi kör testerna.

### Nätverks server

Denna applikation innehåller kod till en nätverksserver som användaren kan ansluta till som klient med TCP
Sockets. Till servern kan man sedan skicka kommandon i form av textsträngar kodade i utf-8. 
När servern mottager ett kommando kommer den att skicka ut det till alla anslutna klienter inklusive
den som skickade in kommandot och på så sätt får man en applikation som är kompatibel
med andras och som gör det möjligt att starta fler exemplar av applikationen och ta emot kommandon som servern skickar ut. När det
kommer in ett kommando att tex rita en cirkel läggs det till i den egna applikation som att utritningen
skett lokalt. På det sättet kan flera användare sitta vid var sin dator
och rita på en gemensam bild och alla ser hela tiden allt som händer. Som extra funktion finns möjligheten att chatta med varandra från applikationen med hjälp av samma server som man redan är uppkopplad till. Denna anvönds sedan även till att att överföra textmeddelanden och visa
dessa i en lista.

### By constructing this program I learnt alot about the following

#### MVC Pattern - Model, View, Controller
> An architectural pattern commonly used for developing user interfaces that divide the related program logic into three interconnected elements
> - Model: Containing business logic that defines how data can be accessed, created, altered and stored
> - View: Containing FXML code that defines where various elements are dispositioned
> - Controller: Containing View logic that defines how information is displayed and how it can be interacted with 
#### Event handelers
> - Used for notifying the application when user has withtaken some actions and redirects these to a specipic target
> - UserKeyEvent, MouseEvent, Action Event, Drag & Drop Events, Key Event, Window Event etc
#### Bindings and properties
> - For creating direct relations between variables so when changes occur in one object this is automatically detected in another one 
#### JUnit testing
> - Writing tests that checks so that various functionalities within the code renders the correct result when executed 
> - Testing for different conditions with Assert class 
#### File handeling
> - To implement a File Chooser that allows user to navigate amongst files and save a certain file with a selected name and format at a selected destination 
> - Convert and save objects into csv format 
#### UI
> - Writing **FXML** for an enhanced user experience
> - Working with **Scene Builder** that generates FXML markup live
> - **Canvas API** and 2D and 3D graphics
> - Enbed Css code into JavaFX to enhance styling
> - UI Controls slider
