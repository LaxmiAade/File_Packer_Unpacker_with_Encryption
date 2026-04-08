// File Packer and Unpacker with Encryption Project.........

import java.io.*;
import java.util.Scanner;

//--------------------- PACKING FUNCTION ---------------------------//
class Packer
{
    public static void Packing() throws Exception
    {
        String Header = null;

        byte Key = 0x11;

        int iRet = 0;
        int i = 0, j = 0;

        byte Buffer[] = new byte[1024];
        byte bHeader[] = new byte[100];

        Scanner sobj = new Scanner(System.in);

        System.out.println("Enter the Name of Folder : ");
        String FolderName = sobj.nextLine();

        System.out.println("Enter the Name of Packed File : ");
        String PackName = sobj.nextLine();

        File fobj = new File(FolderName);

        if((fobj.exists()) && (fobj.isDirectory()))
        {
            File PackObj = new File(PackName);

            PackObj.createNewFile();

            FileOutputStream foobj = new FileOutputStream(PackObj);

            FileInputStream fiobj = null;

            System.out.println("Folder is Present..");

            File fArr[] = fobj.listFiles();

            System.out.println("Number of Files in the Folder are : "+fArr.length);

            for(i = 0; i < fArr.length; i++)
            {
                fiobj = new FileInputStream(fArr[i]);

                if(fArr[i].getName().endsWith(".txt"))
                {
                    // Header Formation 
                    Header = fArr[i].getName() + " " + fArr[i].length();
                
                    for(j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }

                    bHeader = Header.getBytes();

                    // Write Header into Pack File...
                    foobj.write(bHeader,0,100);

                    // Read the Data from input Files from the Mrvellous Folder...
                    while((iRet = fiobj.read(Buffer)) != -1)
                    {
                        // Encryption Logic 

                        for(j = 0; j < iRet; j++)
                        {
                            Buffer[j] = (byte)(Buffer[j] ^ Key);
                        }
                        
                        // Write the Files Data into the File...
                        foobj.write(Buffer,0,iRet);
                    } 
                }
                fiobj.close();
            }
        }
        else
        {
            System.out.println("Error : There is no Such Folder..");
        }
    }
}

//----------------------- UNPACKING FUNCTION ----------------------------//
class Unpacker
{
    public static void Unpacking() throws Exception
    {
        //Variable Creation
        int FileSize = 0;
        int i = 0;
        int iRet = 0;

        byte Key = 0x11;

        Scanner sobj = null;
        String FileName = null;
        File fpackobj = null;
        File fobj = null;  

        // new files object
        FileInputStream fiobj = null;
        FileOutputStream foobj = null;

        byte bHeader[] = new byte[100];
        byte Buffer[] = null;

        String Header = null;
        String Tokens[] = null;

        sobj = new Scanner(System.in);

        System.out.println("Enter the Name of Packed File : ");
        FileName = sobj.nextLine();

        fpackobj = new File(FileName);

        if(fpackobj.exists() == false)
        {
        System.out.println("Error : There is no Such Packed File");
        return;
        }

        fiobj = new FileInputStream(fpackobj);          // for read

        // Read the Header
        while((iRet = fiobj.read(bHeader,0,100)) != -1) 
        { 
        Header = new String(bHeader);

        Header = Header.trim();

        Tokens = Header.split(" ");

        System.out.println("File Name : "+Tokens[0]);
        System.out.println("File Size : "+Tokens[1]);

        fobj = new File(Tokens[0]);

        fobj.createNewFile();

        foobj = new FileOutputStream(fobj);     // for Write

        FileSize = Integer.parseInt(Tokens[1]);

        // Buffer for Reading the Data
        Buffer = new byte[FileSize];

        // Read from Packed File
        fiobj.read(Buffer,0,FileSize);

        // Decrypt the data
        for(i = 0; i < FileSize; i++)
        {
        Buffer[i] =(byte)(Buffer[i] ^ Key);
        }

        // Write into Extracted file
        foobj.write(Buffer,0,FileSize);  
        foobj.close();
        }
        fiobj.close();
    }
}

//-------------------------- MAIN FUNCTION ---------------------------------------//

class PackingUnpacking
{
    public static void main(String A[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("--------------------- Files Packer Unpacker Project -------------------");
        System.out.println("-----------------------------------------------------------------------\n");
        System.out.println("1 : Packing File");
        System.out.println("2 : Unpacking File");
        System.out.println("3: Exit");
        System.out.println("Enter Your Choice : ");

        int Choice = sobj.nextInt();
        sobj.nextLine();

        if(Choice == 1)
        {
            Packer.Packing();
            System.out.println("File Packing Completed successfully...");
        }
        else if(Choice == 2)
        {
            Unpacker.Unpacking();
            System.out.println("File Unpacking Completed Successfully...");
        }
        else if(Choice == 3)
        {
            System.out.println("Thank you for using the Application!");
            System.exit(0);   // Exit the program
        }
        else
        {
            System.out.println("Invalid Choice ");
        }
    }
}