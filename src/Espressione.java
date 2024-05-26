import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

public class Espressione {

	// Stringa che contiene la nostra espressione da valutare
	private final String expression;
	// Contatore delle parentesi che ci serve a verificare alla fine dei calcoli che tutte le parentesi siano state chiuse.
	private int contaParentesi = 0; //conta parentesi

	// Costruttore
	public Espressione(String expression) {

		// Creiamo una stringa che ci permette di controllare che l'espressione da valutare sia valida.
		String EXPR = "(\\d+|[\\+\\-\\*/\\(\\)])+"; //SOLO CN

		// Confrontiamo l'argomento del costruttore con la stringa EXPR che abbiamo appena creato con il metodo ".matches".
		// Essenzialmente con EXPR stiamo definendo una generica stringa che inizia con un numero seguito da un operatore fra quelli definiti.
		// Quindi entriamo nell'if se "expression" non è di questa forma.
		if(!expression.matches(EXPR))
			throw new RuntimeException(expression + " malformata 1.");

		// Se il controllo è andato a buon fine, possiamo salvare l'argomento nella variabile dell'oggetto.
		this.expression = expression;
	}

	// Override del metodo nativo ".toString"
	public String toString() {
		return expression;
	}

	// Metodo generico per far partire il calcolo dell'espressione. Ritorna la soluzione come intero.
	public int calcola() {

		// Creiamo un tokenizer, ovvero un oggetto che è in grado di spezzare in "token" una stringa.
		// Definiamo i 4 operandi e le parentesi come delimitatori e impostiamo true il flag finale, che ci farà valutare anche i delimitatori.
		StringTokenizer stringaToken = new StringTokenizer(expression, "+-*/()", true);

		// Salviamo in una variabile appropriata il risultato del metodo "valutaEspressione" a cui passiamo la nostra stringa token appena creata.
		int risultato = valutaEspressione(stringaToken);

		// Verifichiamo che il numero del contatore parentesi sia 0, ovvero che tutte le parentesi aperte siano state chiuse, altrimenti lanciamo un'eccezione.
		if(contaParentesi != 0)
			throw new RuntimeException(expression + " malformata 6.");

		// Se è tutto ok possiamo ritornare il nostro risultato.
		return risultato;
	}//valuta

	// Prende un char che definisce l'operazione e due interi, ritorna l'intero risultato dell'operazione rilevata.
	private int calcoloAtomico(char operatore, int operando1, int operando2) {
		return switch (operatore) {
			case '+' -> operando1 + operando2;
			case '-' -> operando1 - operando2;
			case '*' -> operando1 * operando2;
			case '/' -> operando1 / operando2;
			default -> throw new RuntimeException(expression + " malformata 3.");
		};
	}


	// Metodo a cui passiamo uno stack di operandi e uno di operatori e che ci ritorna il risultato dell'applicazione ordinata di entrambi.
	// N.B.: Non tiene conto delle parentesi!
	private int applicaOperatori( Stack<Integer> stackOperandi, Stack<Character> stackOperatori ) {

		// Creiamo un ciclo che andrà avanti finché non esauriamo il nostro stack di operatori.
		while(!stackOperatori.isEmpty()) {

			// Creiamo delle variabili che ci serviranno per memorizzare i valori con i quali fare i calcoli.
			char cursoreOperatori = ' '; int operando1 = 0, operando2 = 0; // iniz fittizie

			// Proviamo a caricare nelle variabili appena create operatore e operandi circondandoli da un try/catch in modo che se qualcosa non funziona lanciamo un'eccezione.
			try {
				cursoreOperatori = stackOperatori.pop();
				operando2 = stackOperandi.pop();
				operando1 = stackOperandi.pop();
			} catch(RuntimeException e) {
				System.out.println(expression + " malformata 2.");
				System.exit(-1);
			}
			stackOperandi.push(calcoloAtomico(cursoreOperatori, operando1, operando2));
		}

		// A questo punto controlliamo di aver eseguito tutti gli operandi meno che al massimo uno.
		// Ovvero nello stack operandi dovremmo avere soltanto un numero, il nostro risultato.
		// Se abbiamo finito gli operatori (ovvero siamo usciti dal ciclo) ma abbiamo più di un numero sullo stack degli operandi, vuol dire che c'è un problema.
		if(stackOperandi.size() > 1)
			throw new RuntimeException(expression + " malformata 4.");

		// Ritorniamo il primo elemento dello stack degli operandi, visto che ogni switch case per salvare la sua operazione effettua un push nello stack.
		// Ovvero la soluzione è salvata nello stack stesso in modo da poter eseguire più operazioni alla volta.
		return stackOperandi.pop();
	}//applicaOperatori

	// Creiamo un comparatore di caratteri che prende due caratteri come input e ritorna 0 se non sono operatori fra quelli definiti o se hanno la stessa priorità.
	// Altrimenti ritorna -1 se il secondo operatore ha la precedenza sul primo, viceversa ritorna 1.
	Comparator<Character> comparatoreOperandi =
		(primoOp, secondoOp)-> {
			if( ("" + primoOp).matches("[\\+\\-]") && ("" + secondoOp).matches("[\\*/]") ) return -1;

			if( ("" + primoOp).matches("[\\*/]") && ("" + secondoOp).matches("[\\+\\-]") ) return 1;

			return 0;
		};

	// Metodo ricorsivo a cui viene passata una stringa token e che ritorna il risultato dell'espressione contenuta come intero.
	private int valutaEspressione(StringTokenizer stringaToken) {

		// Creiamo i nostri due stack.
		Stack<Integer> stackOperandi = new Stack<>();
		Stack<Character> stackOperatori = new Stack<>();

		// Creiamo un ciclo che continua finché non esauriamo gli elementi della nostra espressione salvata nella stringa token.
		while(stringaToken.hasMoreTokens()) {

			// Creiamo una stringa cursore nella quale salveremo ad ogni istanza del ciclo il token seguente.
			// N.B.: il metodo ".nextToken" fa scorrere il riferimento salvato nell'oggetto al quale viene applicato.
			// Questo vuol dire che ogni volta che lo usiamo, "stringaToken" contiene una sotto-stringa sempre più piccola.
			// Essenzialmente ogni volta che "usiamo" un token, lo consumiamo.
			String cursoreToken = stringaToken.nextToken();

			// Se il nostro cursore è un numero, lo salviamo nello stack degli operandi.
			if(cursoreToken.matches("\\d+"))
				stackOperandi.push(Integer.parseInt(cursoreToken));
			// Se è una parentesi aperta invece, aumentiamo il contatore delle parentesi e poi facciamo una chiamata ricorsiva.
			// Salviamo il risultato in un intero e poi facciamo una push sullo stack operandi.
			else if(cursoreToken.charAt(0) == '(') {
				contaParentesi++;
				int res = valutaEspressione(stringaToken);
				stackOperandi.push(res);
			}
			// Se il nostro cursore è una parentesi chiusa, diminuiamo il contatore parentesi e chiamiamo il metodo "applicaOperatori" che ci calcolerà il risultato della parentesi.
			// Notiamo che stiamo facendo una return. Questo perché la funzione è ricorsiva.
			// Ogni volta che troviamo una parentesi aperta facciamo una chiamata ricorsiva, quindi se siamo ad una parentesi chiusa, nello stack operatori non ci sono parentesi.
			// Ergo dobbiamo ritornare il risultato della parentesi attuale alla chiamata della funzione precedente, che è partita alla parentesi aperta corrispondente.
			else if(cursoreToken.charAt(0) == ')') {
				contaParentesi--;
				return applicaOperatori(stackOperandi, stackOperatori);
			}
			else {

				// Ci creiamo una variabile per tenere traccia dell'operatore che stiamo guardando con il cursore.
				char operatoreCorrente = cursoreToken.charAt(0);

				// Lo stack operatori è vuoto --OR-- l'operatore corrente ha la precedenza sul primo elemento dello stack operatori.
				if(stackOperatori.isEmpty() || comparatoreOperandi.compare(operatoreCorrente, stackOperatori.peek()) > 0)
					stackOperatori.push(operatoreCorrente);
				else {
					// Finché lo stack operatori non è vuoto --AND-- l'operatore corrente non ha la precedenza sul primo operatore dello stack.
					while(!stackOperatori.isEmpty() && !(comparatoreOperandi.compare(operatoreCorrente,stackOperatori.peek()) > 0) ) {

						// Creiamo delle variabili temporanee dove salvare operatori e operandi.
						char operatoreTemp = stackOperatori.pop();
						int operandoTemp2 = stackOperandi.pop(), operandoTemp1 = stackOperandi.pop();

						// Switch case come prima.
						try {
							stackOperandi.push(calcoloAtomico(operatoreTemp, operandoTemp1, operandoTemp2));
						} catch(RuntimeException e) {
							System.out.println(expression + " malformata 5.");
						}
					}
					// Una volta che usciamo vuol dire che una delle due condizioni del while non è più rispettata.
					// Ovvero o abbiamo usato tutti gli operatori, o abbiamo finito gli operatori con precedenza maggiore o uguale al prossimo.
					// Quindi possiamo fare un push e andare avanti.
					stackOperatori.push(operatoreCorrente);
				}
			}
		}
		// Qui abbiamo finito il while che scorreva tutti i token della stringa.
		// Ovvero abbiamo considerato tutta l'espressione.
		// Dobbiamo ricordarci che rimarremo con delle ultime operazioni da effettuare negli stack.
		// Facciamo un'ultima chiamata ad "applicaOperatori" per concludere.
		return applicaOperatori(stackOperandi, stackOperatori);
	}//valutaEspressione

	public static void main(String[] args) {

		String e0 = "2+5*3";
		String e1 = "(2+7)*5-12+2";
		String e2 = "((12-3)/2+4)*5";
		String e3 = "(12-3)";
		String e4 = "5*(2+7)*((4-2)*8)+5";
		String e5 = "1-2+3-4";
		String e6 = "5*(2+7)-12/7+2";

		System.out.println(e0 + "=" + new Espressione(e0).calcola());
		System.out.println(e1 + "=" + new Espressione(e1).calcola());
		System.out.println(e2 + "=" + new Espressione(e2).calcola());
		System.out.println(e3 + "=" + new Espressione(e3).calcola());
		System.out.println(e4 + "=" + new Espressione(e4).calcola());
		System.out.println(e5 + "=" + new Espressione(e5).calcola());
		System.out.println(e6 + "=" + new Espressione(e6).calcola());
	}//main
}//Espressione
