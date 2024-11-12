//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Ui.TKDUI;
import Controller.TKDController;
import Service.TKD_Service;
import Model.*;
import Repo.*;
public class Main {
    public static void main(String[] args) {

        /*
         * 1. Clasa Persoana (Nume, Prenume, Email, Adresa, Varsta, Centura -> 2 constructore)
         *  Subclase Clasa Persoana: Antrenori, Cursanti, Parinti
         *
         * 2. Clasa Session-TKD (Nivel -> enum, nr Maxim Participanti, Antrenor, Pret pe sedinta)
         *
         * 3.------------------------------- Orar (Grupa -> enum, Zi, interval) map: orar['Luni']
         *
         * atribut pentru Cursanti:  Lista Prezente care contine un hash map => [4. Zi (string)][luna][Data][Pret (int)] = true/false hash map
         *
         * 5. Competitii (Nume, Data, Tara, Oras, Pret)
         *
         * 6. Cantonament (Data, nr Participanti )
         *
         * 7. Examen de centura (Data, Nive_Urmator, Examinator, Lista Participanti)
         *
         * 8. Scop/Monitorizare sportiv
         *
         * a) Factura (Copil, Parinte, )
         *
         *
         *
         *
         * */


        InMemoryRepo<Student> studentRepo = new InMemoryRepo<>();
        InMemoryRepo<Parent> parentRepo = new InMemoryRepo<>();
        InMemoryRepo<Session> sessionRepo = new InMemoryRepo<>();
        InMemoryRepo<Contest> contestRepo = new InMemoryRepo<>();
        InMemoryRepo<Trainer> trainerRepo = new InMemoryRepo<>();
        InMemoryRepo<BeltExam> beltExamRepo = new InMemoryRepo<>();
        InMemoryRepo<TrainingCamp> trainingCampRepo = new InMemoryRepo<>();



        TKD_Service tkdService = new TKD_Service(studentRepo,trainerRepo,parentRepo,sessionRepo,contestRepo,trainingCampRepo);
        TKDController tkdController = new TKDController(tkdService);
        TKDUI newUi = new TKDUI(tkdController);

        newUi.start();



    }
}