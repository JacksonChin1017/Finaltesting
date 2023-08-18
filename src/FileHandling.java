import java.io.*;
import java.util.*;

public class FileHandling implements Serializable {
    private final String fileName;
    private final File file;

    public FileHandling(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while creating file " + fileName);
        }
    }

    private List<String> getHeader() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String firstLine = br.readLine();
            br.close();
            return Arrays.asList(firstLine.split(","));
        } catch (IOException e) {
            System.out.println("Error occurred while getting header");
        }
        return new ArrayList<>();
    }

    private int columnNumber() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String firstLine = br.readLine();
            String[] headerCol = firstLine.split(",");
            br.close();
            return headerCol.length;
        } catch (IOException e) {
            System.out.println("Error occurred while counting column");
            return -1;
        }
    }

    public int getRows() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String firstLine = br.readLine();
            int lineCount = 0;
            while ((firstLine = br.readLine()) != null) {
                lineCount++;
            }
            br.close();
            return lineCount;
        } catch (IOException e) {
            System.out.println("Error occurred while counting rows");
            return -1;
        }
    }

    public void addRow(String... data) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            if (data.length == columnNumber() && data[0].length() < 15) {
                String[] myArr = Arrays.stream(data).toArray(String[]::new);
                pw.println(String.join(",", myArr));
            } else {
                throw new IOException();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error occurred while adding row");
        }
    }

    public String searchRow(String colName, String data) {
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine(); // Jump the header line
            while ((currentLine = br.readLine()) != null) {
                String[] tempArr = currentLine.split(",");
                if (tempArr[colIndex].equals(data)) {
                    br.close();
                    return currentLine;
                }
            }
            br.close();
            return null;
        } catch (IOException e) {
            System.out.println("Error occurred while searching row");
        }
        return null;
    }

    public String searchRow(String colName1, String colName2, String data1, String data2) {
        try {
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine(); // Jump the header line
            while ((currentLine = br.readLine()) != null) {
                String[] tempArr = currentLine.split(",");
                if (tempArr[colIndex1].equals(data1) && tempArr[colIndex2].equalsIgnoreCase(data2)) {
                    br.close();
                    return currentLine;
                }
            }
            br.close();
            return null;
        } catch (IOException e) {
            System.out.println("Error occurred while searching row");
        }
        return null;
    }

    public ArrayList<String> searchMultipleRow(String colMatchName, String matchData, String neededColData) {
        try {
            ArrayList<String> neededData = new ArrayList<>();
            int colIndex = getHeader().indexOf(colMatchName);
            int neededColIndex = getHeader().indexOf(neededColData);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine(); // Jump the header line
            while ((currentLine = br.readLine()) != null) {
                String[] tempArr = currentLine.split(",");
                if (tempArr[colIndex].equals(matchData)) {
                    neededData.add(tempArr[neededColIndex]);
                }
            }
            br.close();
            return neededData;
        } catch (IOException e) {
            System.out.println("Error occurred while searching row");
        }
        return null;
    }

    //    - colName (The colName for matching)
    //    - editCol (The colName for editing)
    public void editRow(String matchColumn, String data, String editColumn, String overrideContent) {
        try {
            boolean found = false;
            int matchColumnIndex = getHeader().indexOf(matchColumn);
            FileReader fr = new FileReader(fileName);
            int editCoIndex = getHeader().indexOf(editColumn);
            BufferedReader br = new BufferedReader(fr);

//            - Temp File writer
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String currentLine; // Jump the header line
            String[] tempArr;
            while ((currentLine = br.readLine()) != null) {
                tempArr = currentLine.split(",");
                if (tempArr[matchColumnIndex].equals(data)) {
                    found = true;
                    tempArr[editCoIndex] = overrideContent;
                    currentLine = String.join(",", tempArr);
                }
                pw.println(currentLine);
            }
            br.close();
            pw.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
            } else {
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while editing row");
        }
    }

    public void editRow(String colName1, String data1, String colName2, String data2, String editCol, String overrideContent) {
        try {
            boolean found = false;
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            FileReader fr = new FileReader(fileName);
            int editCoIndex = getHeader().indexOf(editCol);
            BufferedReader br = new BufferedReader(fr);

            //            - Temp File writer
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String currentLine;  // Jump the header line
            String[] tempArr;
            while ((currentLine = br.readLine()) != null) {
                tempArr = currentLine.split(",");
                if (tempArr[colIndex1].equals(data1) && tempArr[colIndex2].equals(data2)) {
                    tempArr[editCoIndex] = overrideContent;
                    currentLine = String.join(",", tempArr);
                    found = true;
                }
                pw.println(currentLine);
            }
            br.close();
            pw.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
            } else {
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while editing row");
        }
    }


    public void editRow(String colName1, String data1, String colName2, String data2,String colName3,String data3, String editCol, String overrideContent) {
        try {
            boolean found = false;
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            int colIndex3 = getHeader().indexOf(colName3);
            FileReader fr = new FileReader(fileName);
            int editCoIndex = getHeader().indexOf(editCol);
            BufferedReader br = new BufferedReader(fr);

            //            - Temp File writer
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String currentLine;  // Jump the header line
            String[] tempArr;
            while ((currentLine = br.readLine()) != null) {
                tempArr = currentLine.split(",");
                if (tempArr[colIndex1].equals(data1) && tempArr[colIndex2].equals(data2) && tempArr[colIndex3].equals((data3))) {
                    tempArr[editCoIndex] = overrideContent;
                    currentLine = String.join(",", tempArr);
                    found = true;
                }
                pw.println(currentLine);
            }
            br.close();
            pw.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
            } else {
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while editing row");
        }
    }


    public void deleteRow(String colName, String data) {
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = "";
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[colIndex].equals(data)) {
                    found = true;
                    continue;
                }
                pw.println(line);
            }
            pw.close();
            br.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));

            } else {
                tempFile.delete();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while deleting row");
        }
    }

    public void deleteRow(String colName1, String data1, String colName2, String data2) {
        try {
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = "";
            int deletedRowNum = 0;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[colIndex1].equals(data1) && line.split(",")[colIndex2].equals(data2)) {
                    found = true;
                    deletedRowNum++;
                    continue;
                }
                pw.println(line);
            }
            pw.close();
            br.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
                if (deletedRowNum > 1) {
                    System.out.printf("%d rows was deleted.%n", deletedRowNum);
                } else {
                    System.out.println("1 row was deleted");
                }
            } else {
                tempFile.delete();
                System.out.println("Data Not Found");
            }
        } catch (IOException e) {
            System.out.println("Error occurred while deleting row");
        }
    }

    public void groupPrinting() {
        try {
//            - Used to identify the price column and add RM in front
            ListIterator<String> headerIterator = getHeader().listIterator();
            List<List<String>> groupedData = new ArrayList<>();
            List<String> lines = new ArrayList<>();
            int priceColumnIndex = -1;
            int count = 0, counter = 0;
            while (headerIterator.hasNext()) {
                var element = headerIterator.next();
                if (element.toLowerCase().contains("price")) {
                    priceColumnIndex = count;
                    break;
                } else {
                    count++;
                }
            }
//            - Start Printing Row By Row in the text file
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] dataRow = line.split(",");
//            - Printing Heading in the text file
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 20));
            for (String data : dataRow) {
                System.out.print(ConsoleTextDisplay.alignMiddle(data, 20));
            }
            System.out.println();
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 20));
            //            - Start printing data row by row
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
//            =====================
            for (String data : lines) {
                String[] attributes = data.split(",");
                String itemID = attributes[0];

                boolean foundGroup = false;
                for (List<String> group : groupedData) {
                    String[] groupAttributes = group.get(0).split(",");
                    String groupItemID = groupAttributes[0];

                    if (itemID.equals(groupItemID)) {
                        group.add(data);
                        foundGroup = true;
                        break;
                    }
                }

                if (!foundGroup) {
                    ArrayList<String> newGroup = new ArrayList<>();
                    newGroup.add(data);
                    groupedData.add(newGroup);
                }
            }

            // Print the nested ArrayList
            for (List<String> group : groupedData) {
//                - Group Level
                System.out.println(++counter + ".");
                for (String s : group) {
//                    - Line Level
                    dataRow = s.split(",");
                    for (int j = 0; j < dataRow.length; j++) {
                        if (j != priceColumnIndex) {
                            System.out.print(ConsoleTextDisplay.alignMiddle(dataRow[j], 20));
                        } else {
//                        - Add RM at the front if it is a price column
                            System.out.print(ConsoleTextDisplay.alignMiddle("RM " + dataRow[j], 20));
                        }
                    }
                    System.out.println();
                }
                System.out.println(ConsoleTextDisplay.starSignDivider(columnNumber() * 20));
            }
//            =====================
            br.close();
        } catch (IOException e) {
            System.out.println("Error occurred while printing data");
        }
    }

    public void printData() {
        try {
//            - Used to identify the price column and add RM in front
            ListIterator<String> headerIterator = getHeader().listIterator();
            int priceColumnIndex = -1;
            int count = 0;
            while (headerIterator.hasNext()) {
                var element = headerIterator.next();
                if (element.toLowerCase().contains("price")) {
                    priceColumnIndex = count;
                    break;
                } else {
                    count++;
                }
            }
//            - Start Printing Row By Row in the text file
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] dataRow = line.split(",");
//            - Printing Heading in the text file
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 20));
            for (String data : dataRow) {
                System.out.print(ConsoleTextDisplay.alignMiddle(data, 20));
            }
            System.out.println();
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 20));
//            - Start printing data row by row
            while ((line = br.readLine()) != null) {
                dataRow = line.split(",");
                for (int i = 0; i < dataRow.length; i++) {
                    if (i != priceColumnIndex) {
                        System.out.print(ConsoleTextDisplay.alignMiddle(dataRow[i], 20));
                    } else {
//                        - Add RM at the front if it is a price column
                        System.out.print(ConsoleTextDisplay.alignMiddle("RM " + dataRow[i], 20));
                    }
                }
                System.out.println();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error occurred while printing data");
        }
    }

    public void printData(String colName, String matchValue) {
        try {
            int colIndex = getHeader().indexOf(colName);
//            - Used to identify the price column and add RM in front
            ListIterator<String> headerIterator = getHeader().listIterator();
            int priceColumnIndex = -1;
            int count = 0;
            while (headerIterator.hasNext()) {
                var element = headerIterator.next();
                if (element.toLowerCase().contains("price")) {
                    priceColumnIndex = count;
                    break;
                } else {
                    count++;
                }
            }
//            - Start Printing Row By Row in the text file
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] dataRow = line.split(",");
//            - Printing Heading in the text file
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 22));
            for (String data : dataRow) {
                System.out.print(ConsoleTextDisplay.alignMiddle(data, 22));
            }
            System.out.println();
            System.out.println(ConsoleTextDisplay.equalSignDivider(columnNumber() * 22));
//            - Start printing data row by row
            while ((line = br.readLine()) != null) {
                dataRow = line.split(",");
                if (dataRow[colIndex].equalsIgnoreCase(matchValue)) {
                    for (int i = 0; i < dataRow.length; i++) {
                        if (i != priceColumnIndex) {
                            System.out.print(ConsoleTextDisplay.alignMiddle(dataRow[i], 22));
                        } else {
//                        - Add RM at the front if it is a price column
                            System.out.print(ConsoleTextDisplay.alignMiddle("RM " + dataRow[i], 22));
                        }
                    }
                    System.out.println();
                } else continue;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error occurred while printing data");
        }
    }

    public static User login(String userID, String userPassword) {
        try {
            FileInputStream fis = new FileInputStream("Users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                User temp = null;
                Object obj = ois.readObject();
                if (obj != null) {
                    temp = (User) obj;
                    if (userID.equals(temp.getUserID()) && userPassword.equals(temp.getUserPassword())) {
                        return temp;
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public String generateID() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<String> ids = new ArrayList<>();


            while ((line = br.readLine()) != null) {
                String[] dataRow = line.split(",");
                ids.add(dataRow[0]);
            }
            br.close();
//            - Identify the prefix of the ID
            String prefix = getPrefix(ids);
//          - Identify the numeric part of the ID
            int largesttNumericPart = 0;
            if (!ids.isEmpty()) {
                for (String id : ids) {
                    if (id.startsWith(prefix)) {
                        String numericPart = id.substring(prefix.length());
                        int numericValue = Integer.parseInt(numericPart);
                        largesttNumericPart = Math.max(numericValue, largesttNumericPart);
                    }
                }
            }

            String newNumericPart = String.format("%03d", largesttNumericPart + 1);
            return prefix + newNumericPart;


        } catch (IOException e) {
            System.out.println("Error occurred while generateID");
        }
        return "wrongID";
    }

    private String getPrefix(ArrayList<String> ids) {
        String prefix;
        try {
            prefix = ids.get(0).substring(0, 1);
        } catch (Exception e) {
            prefix = switch (fileName) {
                case "Item.txt" -> "I";
                case "ItemSales.txt", "Supplier.txt" -> "S";
                case "PurchaseOrder.txt" -> "o";
                case "PurchaseRequisition.txt" -> "P";
                default -> "N";
            };
        }
        return prefix;
    }
}