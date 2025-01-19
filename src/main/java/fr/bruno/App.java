package fr.bruno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class App {

	private List<Integer> numeros;

	private List<Double> numerosMoyenne10 = new ArrayList<>();

	private List<Integer> numerosTrie;

	private List<Integer> test1 = new ArrayList<>();
	private List<Double> test1_10 = new ArrayList<>();

	public List<Integer> genererNumeros(int quantite) {
		List<Integer> listeNumeros = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < quantite; i++) {
			listeNumeros.add(random.nextInt(200));
		}

		return listeNumeros;
	}

	public Double calculerMoyennesGlissantes(List<Integer> liste, int position, int tailleFenetre) {

		if (position < 0 || position >= liste.size()) {
			throw new IllegalArgumentException("La position doit être dans les limites de la liste.");
		}

		int debutFenetre = Math.max(0, position - tailleFenetre + 1);
		int finFenetre = Math.min(liste.size() - 1, position);

//		System.out.println("debutFenetre : " + debutFenetre);
//		System.out.println("finFenetre : " + finFenetre);

		double somme = 0;
		int count = 0;
		for (int i = debutFenetre; i <= finFenetre; i++) {
			somme += liste.get(i);
			count++;
		}

//		System.out.println("count : " + count);
//		System.out.println("somme / count : " + somme / count);

		return somme / count;
	}

	private void createNewList1(double moyenne) {

		List<Integer> numerosTrieTmp = new ArrayList<Integer>(numerosTrie);

		while (!numerosTrieTmp.isEmpty()) {

			if (test1.isEmpty()) {
				test1.add(numerosTrieTmp.getLast());
				numerosTrieTmp.removeLast();
			}

			double moyenneTest1 = computeMoyenne(test1);

			if (moyenneTest1 > moyenne) {
				test1.add(numerosTrieTmp.getFirst());
				numerosTrieTmp.removeFirst();
			} else {
				test1.add(numerosTrieTmp.getLast());
				numerosTrieTmp.removeLast();
			}

		}

	}

	private void compute() {

		List<Integer> numeros = genererNumeros(100);

		numerosTrie = new ArrayList<>(numeros);
		Collections.sort(numerosTrie);

		double moyenne = computeMoyenne(numeros);
		System.out.println("Moyenne\t" + moyenne);

		int min = Collections.min(numeros);
		System.out.println("Minimum\t" + min);

		int max = Collections.max(numeros);
		System.out.println("Maximum\t" + max);

		double variance = numeros.stream().mapToDouble(num -> Math.pow(num - moyenne, 2)).average().orElse(0);
		// System.out.println("Variance: " + variance);

		double ecartType = Math.sqrt(variance);
		System.out.println("Écart type\t" + ecartType);

		Collections.sort(numerosTrie);

		createNewList1(moyenne);

		int n = 0;
		for (int i = 0; i < numeros.size(); i++) {
			int numero = numeros.get(i);
			int numeroTrie = numerosTrie.get(i);

			Double val10 = calculerMoyennesGlissantes(numeros, i, 10);
			numerosMoyenne10.add(val10);

			int test1Number = test1.get(i);
			Double test1Val10 = calculerMoyennesGlissantes(test1, i, 10);
			test1_10.add(test1Val10);

			String log = ++n + "\t" + numero + "\t" + val10 + "\t" + numeroTrie + "\t\t" + test1Number + "\t"
					+ test1Val10;
			System.out.println(log.replace('.', ','));

		}

	}

	private double computeMoyenne(List<Integer> numeros) {
		return numeros.stream().mapToInt(Integer::intValue).average().orElse(0);
	}

	public static void main(String[] args) {
		App app = new App();
		app.compute();

	}
}
