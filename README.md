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

## Projektbeskrivning

> Detta är en applikation med grafiskt gränssnitt konstruerad med JavaFX enligt MVC - Model View Controller. Koden har tester skrivna med Junit5. Det är ett ritprogram där användaren kan rita på en Canvas i JavaFX (GUI), och spara ritade objekt i SVG-format. Applikationen innehåller bland annat designmönster och nätverkskommunikation.

### MVC

> Applikationen är implemeterad som en MVC applikation

![Screenshot 2023-03-02 at 20 57 53](https://user-images.githubusercontent.com/103879144/222538677-38c1cd5b-f2fd-4951-b98b-9f1b6dd1d145.png)

### Databindings och properties 

> Properties gör det bland annat möjligt att koppla ihop scenen så att vyn uppdateras automatiskt när man modifierar datan som finns bakom den. Varje property wrappar ett existerande Java objekt och adderar funktionalitet för lyssnande och bindningar.

### Canvas och shapes

> Canvas är en bild som kan ritas på genom att använda ett set av grafiska kommandon som erbjuds av GraphicsContext. En canvas node kontrueras med bredd och höjd som specificerar storleken på en bild på vilken canvas ritkommandon renderas.

> Förutom fri ritning kan olika shapes ritas ut valfritt antal gånger i programmet. Nya shapes skapas med hjälp av en factory / builder pattern och shapes som ritas ut representeras av en objekthierarki under programmets körning. Storlek och färg på shapes kan sättas på objektet som ska ritas ut. Utritning av nya shapes sker genom att klicka med musen där objektet ska ritas ut. Redan utritade objekt kan sedan väljas genom att gå över i select mode och klicka på skärmen, på den valda shapen kan man sedan ändra färg och storlek.

### Undo/redo

> Programmet ger möjlighet att ångra senaste ändringen man gjort. Ritar man tex en ny shape kan en
undo ta bort den senast ritade shapen och en redo kan återinföra ändringen man gjort. Applikationen innehåller en mer avancerad
lösning som implementerar Multilevel Undo och Redo genom knapptryckningar överst i fönstret implementerat med Command Pattern och Stack

### Gränssnitt

> Gränssnittet för att välja inställningar, färg och shape görs med JavaFX Controllers så som Button, DropDown, Menu mfl.

### Export av/spara ritade objekt 

> Export av utritade objekt sker som svg-format och där användaren tillåts välja filnamn för sparning.

### Tester

> Applikationen innehåller Unit Tester med hjälp av JUnit5. 

### Nätverks server

> Användaren kan som klient via en nätverksserver ansluta med TCP
Sockets. På det sättet kan flera användare sitta vid var sin dator
och rita på en gemensam bild och alla ser hela tiden allt som händer. Som extra funktion finns möjligheten att chatta med varandra från applikationen med hjälp av samma server som man redan är uppkopplad till. 


