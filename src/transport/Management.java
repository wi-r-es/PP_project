package transport;

import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;
import hr.LicenseType;


import java.util.Arrays;

public class Management implements IManagement {

    private IItem[] ItemsList = new IItem[MAXITEMS];
    private IVehicle[] VehiclesList = new IVehicle[MAXVEHICLES];
    private IDriver[] DriversList = new IDriver[MAXDRIVERS];
    private IDelivery[] DeliveriesList = new IDelivery[MAXDELIVERIES];

    private final static int MAXITEMS = 100;
    private final static int MAXVEHICLES = 30;
    private final static int MAXDRIVERS = 30;
    private final static int MAXDELIVERIES = 100;

    // Methods
    @Override
    public boolean addItem(IItem var1) throws ManagementException {
        if( var1.getStatus().equals(ItemStatus.NON_DELIVERED)){
            if( var1.getTransportationTypes()==null) {
                throw new ManagementException("Item have no transportation type defined...");
            } else {
                if(ItemsList[0] == null){
                    try{
                        ItemsList[0] = var1;
                        return true;
                    }catch (ArrayStoreException e){
                        System.err.println("Wrong type of object to store...");
                    }
                } else {
                    boolean ItemNonExistence = false;
                    try{
                        for(IItem tempItem : ItemsList ){ //deep check if item already exists in inventory
                            if( !( ( tempItem.getReference().equals(var1.getReference()) ) && ( tempItem.getDescription().equals(var1.getDescription()) ) && ( tempItem.getCustomer().equals(var1.getCustomer() ))) ){
                                ItemNonExistence = true;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.err.println("Index out of bounds...");
                    }
                    if( ItemNonExistence ){
                        try {
                            for( int i =1; i< ItemsList.length ; i++){
                                if(ItemsList[i] == null){
                                    if( ItemsList[i-1] != null  && ItemsList[i+1] == null ){
                                            ItemsList[i] = var1;
                                            return true;
                                        }else {Arrays.sort(ItemsList, i-1 , ItemsList.length); i--;}
                                }
                                if ( i==ItemsList.length-1 ){
                                    ItemsList = Arrays.copyOf(ItemsList, ItemsList.length+1); //does this work correctly?
                                    ItemsList[ItemsList.length-1] = var1;
                                    return true;
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.err.println("Index out of bounds...");
                        } catch (ArrayStoreException e){
                            System.err.println("Wrong type of object to store...");
                        }
                    } else return false;
                }
            }
        }else {
            throw new ManagementException("Item Status isn't correct...");

        }
        throw new ManagementException("Parameter is null");
    }


    @Override
    public boolean removeItem(IItem var1) throws ManagementException {

        if(ItemsList.length == 0){
                System.out.println("No Item to be removed...");
                return false;
            }
         else {
            try {
                for( int i =0; i< ItemsList.length ; i++){
                    if(ItemsList[i].equals(var1)){
                        if( ItemsList[i].getReference().equals(var1.getReference()) && ItemsList[i].getCustomer().equals(var1.getCustomer()) ){ //deep check the condition validated before
                            ItemsList[i]=null;
                            Arrays.sort(ItemsList, i, ItemsList.length);
                            return true;
                        } else return false;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
                return false;
            }
        }
        throw new ManagementException("Parameter is null");
    }


    @Override
    public IItem[] getItems() {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        }  else {
            try {
                int counter= 0;
                for( int i =0; i< ItemsList.length ; i++){
                    if
                    ( ItemsList[i] == null){ counter = i; }
                    else if( i ==  MAXITEMS-1 ) { counter = i; }
                }
                return Arrays.copyOfRange(ItemsList, 0, counter);
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public IItem[] getItems(ICustomer var1) {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        } else {
            try {
                int numOfItems = 0;
                int[] ItemsIndexes = new int[25];
                for( int i =0; i< ItemsList.length ; i++){
                    if ( ItemsList[i].getCustomer().equals(var1) ){
                        ItemsIndexes[numOfItems++]=i; // so e incrementado depois de adicionado ?
                    }
                }
                IItem[] ItemsCopy = new IItem[numOfItems];
                for (int i =0; i<= numOfItems; i++) {
                    ItemsCopy[i] = ItemsList[ItemsIndexes[i]];
                }
                return ItemsCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public IItem[] getItems(IDestination var1) {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        } else {
            try {
                int numOfItems = 0;
                int[] ItemsIndexes = new int[25];
                for( int i =0; i< ItemsList.length ; i++){
                    if ( ItemsList[i].getDestination().equals(var1) ){
                        ItemsIndexes[numOfItems++]=i;
                    }
                }
                IItem[] ItemsCopy = new IItem[numOfItems];
                for (int i =0; i<= numOfItems; i++) {
                    ItemsCopy[i] = ItemsList[ItemsIndexes[i]];
                }
                return ItemsCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;

    }

    @Override
    public IItem[] getItems(TransportationTypes var1) {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }

        } else {
            try {
                int numOfItems = 0;
                int[] ItemsIndexes = new int[MAXITEMS];
                for( int i =0; i< ItemsList.length ; i++){
                    TransportationTypes[] tempTT = ItemsList[i].getTransportationTypes( );
                    for (int j = 0; j < tempTT.length; j++) {
                        if ( tempTT[j].equals(var1) ){
                            ItemsIndexes[numOfItems++]=i;
                            break;
                        }
                    }
                }
                IItem[] ItemsCopy = new IItem[numOfItems];
                for (int i =0; i<= numOfItems; i++) {
                    ItemsCopy[i] = ItemsList[ItemsIndexes[i]];
                }
                return ItemsCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public IItem[] getItems(ItemStatus var1) {

        if(ItemsList[0] == null){
            Arrays.sort(ItemsList);
            if (ItemsList[0] == null){
                System.out.println("No Items to be found..."); //double-check if ItemsList is empty
            }
        } else {
            try {
                int numOfItems = 0;
                int[] ItemsIndexes = new int[MAXITEMS];
                for( int i =0; i< ItemsList.length ; i++){
                    if ( ItemsList[i].getStatus().equals(var1) ){
                        ItemsIndexes[numOfItems++]=i;
                    }
                }
                IItem[] ItemsCopy = new IItem[numOfItems];
                for (int i =0; i<= numOfItems; i++) {
                    ItemsCopy[i] = ItemsList[ItemsIndexes[i]];
                }
                return ItemsCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public boolean addVehicle(IVehicle var1) throws ManagementException {

        if (!(var1.getStatus().equals(VehicleStatus.IN_MAINTAINANCE))) {

            if (VehiclesList[0] == null) {
                try{
                    VehiclesList[0] = var1;
                    return true;
                }catch (ArrayStoreException e){
                    System.err.println("Wrong type of object to store...");
                }
            } else {
                boolean VehicleNonExistence = false;
                try {
                    for (IVehicle tempVehicle : VehiclesList) { //deep check if Vehicle already exists in inventory
                        if (!(tempVehicle.getLicensePlate().equals(var1.getLicensePlate()))) {
                            VehicleNonExistence = true;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Index out of bounds...");
                }
                if (VehicleNonExistence) {
                    try {
                        for (int i = 1; i < VehiclesList.length; i++) {
                            if (VehiclesList[i] == null) {
                                if (VehiclesList[i - 1] != null && VehiclesList[i + 1] == null) {
                                    VehiclesList[i] = var1;
                                    return true;
                                } else {
                                    Arrays.sort(VehiclesList, i - 1, VehiclesList.length); i--;
                                }
                                if ( i==VehiclesList.length-1 ){
                                    VehiclesList = Arrays.copyOf(VehiclesList, VehiclesList.length+1); //does this work correctly?
                                    VehiclesList[VehiclesList.length-1] = var1;
                                    return true;
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Index out of bounds...");
                    } catch (ArrayStoreException e) {
                        System.err.println("Wrong type of object to store...");
                    }
                } else return false;
            }
        } else throw new ManagementException("Vehicle is under Maintainance...");
        throw new ManagementException("Parameter is null");
    }

    @Override
    public boolean addDriver(IDriver var1) throws ManagementException {

        if( !(var1.getStatus().equals(DriverStatus.ASSIGNED)) ){
            if(DriversList[0] == null){
                try{
                    DriversList[0] = var1;
                    return true;
                }catch (ArrayStoreException e){
                    System.err.println("Wrong type of object to store...");
                }
            } else {
                boolean DriverNonExistence = false;
                try{
                    for(IDriver tempDriver : DriversList ){ //deep check if Driver already exists in Data-base
                        if( !( tempDriver.getId().equals(var1.getId()) ) ){
                            DriverNonExistence = true;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Index out of bounds...");
                }
                if( DriverNonExistence ){
                    try {
                        for( int i =1; i< DriversList.length ; i++){
                            if(DriversList[i] == null){
                                if( DriversList[i-1] != null  && DriversList[i+1] == null ){
                                    DriversList[i] = var1;
                                    return true;
                                } else {Arrays.sort(DriversList, i-1 , DriversList.length); i--;}
                            }
                            if ( i==DriversList.length-1 ){
                                DriversList = Arrays.copyOf(DriversList, DriversList.length+1); //does this work correctly?
                                DriversList[DriversList.length-1] = var1;
                                return true;
                            }
                        }
                    }  catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Index out of bounds...");
                    } catch (ArrayStoreException e) {
                        System.err.println("Wrong type of object to store...");
                    }
                } else return false;
            }

        } else {
            throw new ManagementException("Driver is already assigned...");

        }
        throw new ManagementException("Parameter is null");
    }

    @Override
    public boolean removeDriver(IDriver var1) throws ManagementException {
        if(DriversList.length == 0){
            System.out.println("No Driver to be removed...");
            return false;
        }
        else {
            try {
                for( int i =0; i< DriversList.length ; i++){
                    if(DriversList[i].equals(var1)){
                        if( DriversList[i].getId().equals(var1.getId()) ){ //deep check the condition validated before
                            DriversList[i]=null;
                            Arrays.sort(DriversList, i, DriversList.length);
                            return true;
                        } return false;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
                return false;
            }
        }
        throw new ManagementException("Parameter is null");
    }

    @Override
    public boolean removeVehicle(IVehicle var1) throws ManagementException {
        if(VehiclesList.length == 0){
            System.out.println("No Vehicle to be removed...");
            return false;
        }
        else {
            try {
                for( int i =0; i< VehiclesList.length ; i++){
                    if(VehiclesList[i].equals(var1)){
                        if( VehiclesList[i].getLicensePlate().equals(var1.getLicensePlate()) ){ //deep check the condition validated before
                            VehiclesList[i]=null;
                            Arrays.sort(VehiclesList, i, VehiclesList.length);
                            return true;
                        } else return false;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
                return false;
            }
        }
        throw new ManagementException("Parameter is null");
    }

    @Override
    public IVehicle[] getFleet() {

        if(VehiclesList[0] == null){
            Arrays.sort(VehiclesList);
            if (VehiclesList[0] == null){
                System.out.println("No Vehicles to be found..."); //double-check if List is empty
            }
        }else {
            try {
                int counter= 0;
                for( int i =0; i< VehiclesList.length ; i++){
                    if
                    ( VehiclesList[i] == null){ counter = i; }
                    else if( i ==  MAXVEHICLES-1 ) { counter = i; }
                }
                return Arrays.copyOfRange(VehiclesList, 0, counter);
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1) {
        if(VehiclesList[0] == null){
            Arrays.sort(VehiclesList);
            if (VehiclesList[0] == null){
                System.out.println("No Vehicles to be found..."); //double-check if List is empty
            }
        } else {
            try {
                int numOfVehicles = 0;
                int[] VehiclesIndexes = new int[MAXVEHICLES];
                for( int i =0; i< VehiclesList.length ; i++){
                    if ( VehiclesList[i].getStatus().equals(var1) ){
                        VehiclesIndexes[numOfVehicles++]=i;
                    }
                }
                IVehicle[] VehiclesCopy = new IVehicle[numOfVehicles];
                for (int i =0; i<= numOfVehicles; i++) {
                    VehiclesCopy[i] = VehiclesList[VehiclesIndexes[i]];
                }
                return VehiclesCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public IVehicle[] getFleet(TransportationTypes var1) {
        if(VehiclesList[0] == null){
            Arrays.sort(VehiclesList);
            if (VehiclesList[0] == null){
                System.out.println("No Vehicles to be found..."); //double-check if List is empty
            }
        } else {
            try {
                int numOfVehicles = 0;
                int[] VehiclesIndexes = new int[MAXVEHICLES/2];
                for( int i =0; i< VehiclesList.length ; i++){
                    TransportationTypes[] tempTT = VehiclesList[i].getTransportationTypes( );
                    for (int j = 0; j < tempTT.length ; j++) {
                        if( tempTT[i].equals(var1) ){
                            VehiclesIndexes[numOfVehicles++]=i;
                            break;
                        }
                    }
                }
                IVehicle[] VehiclesCopy = new IVehicle[numOfVehicles];
                for (int i =0; i< numOfVehicles; i++) {
                    VehiclesCopy[i] = VehiclesList[VehiclesIndexes[i]];
                }
                return VehiclesCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }


    @Override
    public IVehicle[] getFleet(VehicleStatus var1, TransportationTypes var2) {
        if(VehiclesList[0] == null){
            Arrays.sort(VehiclesList);
            if (VehiclesList[0] == null){
                System.out.println("No Vehicles to be found..."); //double-check if List is empty
            }
        } else {
            try {
                int numOfVehicles = 0;
                int[] VehiclesIndexes = new int[MAXVEHICLES/2];
                for( int i =0; i< VehiclesList.length ; i++){
                    if ( VehiclesList[i].getStatus().equals(var1) ){
                        TransportationTypes[] tempTT = VehiclesList[i].getTransportationTypes( );
                        for (int j = 0; j < tempTT.length ; j++) {
                            if( tempTT[i].equals(var2) ){
                                VehiclesIndexes[numOfVehicles++]=i;
                                break;
                            }
                        }
                    }
                }
                IVehicle[] VehiclesCopy = new IVehicle[numOfVehicles];
                for (int i =0; i<= numOfVehicles; i++) {
                    VehiclesCopy[i] = VehiclesList[VehiclesIndexes[i]];
                }
                return VehiclesCopy;
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Index out of bounds...");
            }
        }
        return null;
    }

    @Override
    public boolean addDelivery(IDelivery var1) throws ManagementException {
        if (var1==null){ System.err.println("Parameter is null...");}
        else{
            if (var1.isEmpty()) {
                throw new ManagementException("Delivery is empty..."); //or the items cannot be stored inside the vehicle ??? how the fuck | something com o load mb?
            }
            try {
                IItem[] Itemstemp = var1.getRemainingItems();
                int length = var1.getRemainingItems().length;
                boolean ItemIsOnSystem = true;
                for (int i = 0; i < length; i++) {
                    if (!Itemstemp[i].getStatus().equals(ItemStatus.ASSIGNED)) {
                        throw new ManagementException("Delivery with Items not ASSIGNED...");
                    }
                    for (int j = 0; j < ItemsList.length; j++) {
                        ItemIsOnSystem = Itemstemp[i].getReference().equals(ItemsList[j].getReference());
                    }
                }
                if (!ItemIsOnSystem) {
                    throw new ManagementException("Delivery Item isn't on List...");
                }
                if (var1.getVehicle() == null) {
                    throw new ManagementException("Delivery has no Vehicle assigned...");
                } else {
                    try {
                        for (int i = 0; i < Itemstemp.length; i++) {
                            if (!(Arrays.deepEquals(var1.getVehicle().getTransportationTypes(), Itemstemp[i].getTransportationTypes()))) {
                                throw new ManagementException("Delivery Vehicle Transportation Types don't mach Item Transportation types...");
                            }
                        }
                        LicenseType[] tempLicense = var1.getVehicle().getAllowedLicenses();
                        for (int i = 0; i < tempLicense.length; i++) {
                            if (var1.getDriver() == null || !var1.getDriver().haveLicense(tempLicense[i])) {
                                throw new ManagementException("Delivery has no Driver or Driver cannot drive delivery vehicle...");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Index out of Bounds...");
                    } catch (NullPointerException e) { //in case var1.getVehicle().getAllowedLicenses() returns null
                        System.err.println(e.getMessage());
                    }
                    if ((DeliveriesList[0]==null)){
                        try{
                            DeliveriesList[0] = var1;
                            return true;
                        }catch (ArrayStoreException e){
                            System.err.println("Wrong type of object to store...");
                        }
                    }
                    boolean DeliveryNonExistence = false;
                    try{
                        for(IDelivery tempDelvr : DeliveriesList ){ //deep check if item already exists in inventory
                            if( !(tempDelvr.getId().equals(var1.getId())) ){
                                DeliveryNonExistence = true;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.err.println("Index out of bounds...");
                    }
                    if( DeliveryNonExistence ){
                        try {
                            for( int i =1; i< DeliveriesList.length ; i++){
                                if(DeliveriesList[i] == null){
                                    if( DeliveriesList[i-1] != null  && DeliveriesList[i+1] == null ){
                                        DeliveriesList[i] = var1;
                                        return true;
                                    } else {Arrays.sort(ItemsList, i-1 , ItemsList.length); i--;}
                                }
                                if ( i==DeliveriesList.length-1 ){
                                    DeliveriesList = Arrays.copyOf(DeliveriesList, DeliveriesList.length+1); //does this work correctly?
                                    DeliveriesList[DeliveriesList.length-1] = var1;
                                    return true;
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.err.println("Index out of bounds...");
                        } catch (ArrayStoreException e){
                            System.err.println("Wrong type of object to store...");
                        }
                    } else return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Index out of Bounds...");
            }
        }
        return false;
    }

    @Override
    public void deliveredItem(String var1, String var2) throws Exception {
        if ( var1==null || var2==null ) { throw new Exception("Parameter is null...");}
        try {
            for (IDelivery DeliveryTemp : DeliveriesList) {
                if (var1.equals(DeliveryTemp.getId())) {
                    for (IItem ItemTemp : ItemsList) {
                        if (var2.equals(ItemTemp.getReference())) {
                            if ( !ItemTemp.getStatus().equals(ItemStatus.ASSIGNED) ){ throw new Exception("Item isn't assigned...");}
                            ItemTemp.setStatus( ItemStatus.DELIVERED );
                        }
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        catch (Exception e){
            System.err.println("Error trying to access Item/Delivery");
        }
    }

    @Override
    public void deliveredItem(String var1, IDestination var2) throws Exception {
        if ( var1==null || var2==null ) { throw new Exception("Parameter is null...");}
        try {
            for (IDelivery DeliveryTemp : DeliveriesList) {
                if (var1.equals(DeliveryTemp.getId())) {
                    for (IItem ItemTemp : ItemsList) {
                        if (var2.equals(ItemTemp.getDestination())) {
                            if ( !ItemTemp.getStatus().equals(ItemStatus.ASSIGNED) ){ throw new Exception("Item isn't assigned...");}
                            ItemTemp.setStatus( ItemStatus.DELIVERED );
                        }
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        catch (Exception e){
            System.err.println("Error trying to access Item/Delivery");
        }
    }

    @Override
    public ItemStatus checkItemStatus(String var1) throws Exception {
        try{
            for( IItem temp : ItemsList){
                if( (temp.getReference()).equals(var1) ) return temp.getStatus();
            }
            throw new Exception("Item doesn't exist");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds...");
        }
        return null;
    }

    @Override
    public void startDelivery(String var1) throws DeliveryException {
        try {
            for (IDelivery tempD : DeliveriesList) {
                if (var1.equals(tempD.getId())) {
                    tempD.start();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds");
        }
        throw new DeliveryException(); // confirmar com neves/javadocs
    }

    @Override
    public void stopDelivery(String var1) throws DeliveryException {
        try{
            for( IDelivery tempD : DeliveriesList ){
                if( var1.equals(tempD.getId())){
                    tempD.end();
                }
            }
        }  catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Index out of bounds");
        }
        throw new DeliveryException();
    }
/*
            • Represente uma folha de acompanhamento de um entrega (Delivery) com indicação dos itens a entregar em cada destinatário;
            • Resumo dos itens a entregar por tipo de transporte (TransportationTypes);
            • Resumo do estado da frota de veículos;
            • Resumo do estado dos condutores.
 */

}
