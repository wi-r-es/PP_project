/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package hr;


import exceptions.HRException;
import exceptions.ManagementException;
import transport.DriverStatus;
import transport.IItem;
import transport.ItemStatus;

import java.time.LocalDate;
import java.util.Arrays;

public class Driver extends Person implements IDriver{

    private LocalDate StartingPositionDate;
    private LicenseType[] LicenseList;
    private DriverStatus Status;

    public Driver(Integer id, String name, LocalDate birthDate, IAddress address, hr.LicenseType[] licenseType, DriverStatus status) {
        super(id, name, birthDate, address);
        StartingPositionDate = LocalDate.now();
        LicenseList = null;
        Status = status;
    }

    @Override
    public LocalDate getStartingPositionDate() {
        return StartingPositionDate;
    }

    @Override
    public boolean addLicense(LicenseType var1) throws HRException {
        if( this.getStatus().equals(DriverStatus.FREE) ){
            if(LicenseList[0] == null){
                try{
                    LicenseList[0] = var1;
                }catch (ArrayStoreException e){
                    System.err.println("Wrong type of object to store...");
                }
            } else {
                boolean LicenseNonExistence = this.haveLicense(var1);
                if( LicenseNonExistence ){
                    try {
                        for( int i =1; i< LicenseList.length ; i++){
                            if(LicenseList[i] == null){
                                if( LicenseList[i-1] != null  && LicenseList[i+1] == null ){
                                    LicenseList[i] = var1;
                                    Arrays.sort(LicenseList);
                                    return true;
                                }
                            } else {
                                Arrays.sort(LicenseList, i-1 , LicenseList.length); i--;}
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.err.println("Index out of bounds...");
                    } catch (ArrayStoreException e){
                        System.err.println("Wrong type of object to store...");
                    }
                } else return false;
            }
        } else {
            throw new HRException("Driver isn't FREE...");
        }
        throw new HRException("Parameter is null");
    }

    @Override
    public boolean removeLicense(LicenseType var1) throws HRException {
        if( !(this.getStatus().equals(DriverStatus.FREE)) ){
            throw new HRException("Driver isn't FREE...");
        }
        if(LicenseList.length == 0){
            System.out.println("No License to be removed...");
            return false;
        }
        else {
            try {
                for( int i =0; i< LicenseList.length ; i++){
                    if(LicenseList[i].equals(var1)){
                        if( LicenseList[i].name().equals(var1.name()) ) { //deep check
                            LicenseList[i] = null;
                            Arrays.sort(LicenseList, i-1, LicenseList.length);
                            return true;
                        } else return false;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
                return false;
            }
        }
        throw new HRException("Parameter is null");
    }

    @Override
    public boolean haveLicense(LicenseType var1) {
        try{
            for(LicenseType tempLicense : LicenseList){
                if(tempLicense.equals(var1)) {
                    if (tempLicense.toString().equals(var1.toString())) {
                        return true;
                    }
                }
            } return false;
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of Bounds...");
        }
        return false;
    }

    @Override
    public DriverStatus getStatus() {
        return Status;
    }

    @Override
    public void setStatus(DriverStatus var1) {
        Status = var1;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public IAddress getAddress() {
        return super.getAddress();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public LocalDate getBirthDate() {
        return super.getBirthDate();
    }
}
