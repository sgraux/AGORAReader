import Model.ExtractReader;

public class TestExtractReader {

    public static void main(String [ ] args)
    {
        ExtractReader testExtract = new ExtractReader("C:\\Users\\Sean\\Documents\\STAGE - RATP\\Sheet1CUT.xlsx");
        System.out.println(testExtract.getAnneeIndex(0).getMoisIndex(0).toString());

    }
}
