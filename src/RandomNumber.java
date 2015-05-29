import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RandomNumber {
	
	private static final String FILE_NAME = "credenciais.xlsx";
	
	private static List<String> nomes = new ArrayList<>();
	
	public static void main(String[] args) {
		
		carregaArquivo();
		
		int max = nomes.size();
		
		System.out.println("Serão sorteados numeros entre 1 e " + max);
		System.out.println("Aperte enter para sortear.");
		
		Scanner scanner = new Scanner(System.in);

		String nextLine = scanner.nextLine();

		while (nextLine.isEmpty() && nomes.size() > 0) {
			int posicaoSorteado = generateRandomNumber(nomes.size());
			System.out.println("Participante sorteado: " + posicaoSorteado + " - " + nomes.get(posicaoSorteado));
			removePosicaoDaLista(nomes, posicaoSorteado);
			nextLine = scanner.nextLine();
		}
		scanner.close();
	}
	
	public static void carregaArquivo() {
		try { 
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_NAME));
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			if (sheet != null) {
				Iterator<?> rowIterator = sheet.rowIterator();
				
				while (rowIterator.hasNext()) {
					XSSFRow row = (XSSFRow) rowIterator.next();
					XSSFCell cell = row.getCell(0);
					try {
					String name = cell.getStringCellValue();
					nomes.add(name);
					} catch(RuntimeException e) { 
						continue;
					}
				}
			}
			workbook.close();
		} catch(Exception e) { 
			throw new RuntimeException(e);
		}
		
	}

	private static void removePosicaoDaLista(List<String> numeros, int posicaoSorteado) {
		numeros.remove(posicaoSorteado);
	}

	private static int generateRandomNumber(int max) {
		long seed = System.currentTimeMillis();
		Random random = new Random(seed);
		int posicaoSorteado = random.nextInt(max);
		return posicaoSorteado;
	}

}