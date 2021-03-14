# Traffic-Coordonation

Task 1 - Masinile asteapta timpul precizat, iar apoi trec.
Nu am folosit elemente de sincronizare.

Task 2 - In acest caz, folosim un semafor care sa contorizeze
cate masini ajung in sens (acquire), iar dupa ce il parasesc
semaforul isi recapata valoare (release).

Structuri folosite:
- semafor

Task 3 - Pentru acest caz, exista un semafor cu 1 permit 
pentru fiecare directie, urmand ca apoi sa folosesc aceeasi
structura si pentru urmatoarele doua task-uri.

Structuri folosite:
- arraylist cu semafor

Task 4 - Similar cu situatia de mai sus, insa semafoarele 
pentru directii au permits cate masini sunt permise in sens,
iar barieriele sunt folosite pentru a ne asigura ca avem 
numarul de masini necesare. 

Structuri folosite:
- bariere
- arraylist cu semafoare

Task 5 - Similar cu cel de mai sus, semafoarele setate cu
permits numarul de masini din fiecare directie.

Structuri folosite:
- arraylist cu semafoare

Task 6 - In functie de prioritate, masinile sunt tratate 
diferit. Pentru cele low verificam daca sunt deja masini 
in sens, iar daca nu sunt pot trece. Daca totusi sunt, 
vor astepta pana cand vor fi anuntate de cele cu high 
priority ca toate au iesit din sens, in timp ce vor fi
adaugate intr-o blocking queue pentru a trece mai apoi
pe rand.

Structuri folosite:
- atomic integer
- wait and notifyAll
- blocking queue

Task 7 - Am folosit variabilele oferite de pedestrians, 
finished si pass, pentru alasa masinile sa treaca sau nu. 
Pentru a printa o singura data ma folosesc de prevoutput 
(previous output) pentru a stii ce am printrat inainte. 
Daca ce am printat inainte este diferit de e doresc sa 
printez acum, atunci stim ca este  ok sa printam.

Task 8 - Am folosit cate o coada pentru fiecare directie in
care am adaugat masinile indiferent daca era randul lor sau nu.
Masinile au fost scoase pe rand in functie de randul carei directii
era, iar directia era resetata dupa ce au trecut X masini.

Structuri folosite:
- blocking queue
- bariera

Task 10 - Asteptam ca toate masinile sa ajunga la bariera, 
iar pentru fiecare directie din care vin, aceste sunt 
adaugate in blocking queues diferite.

Structuri folosite:
- bariere
- blocking queue
- sincronizare pentru printare
