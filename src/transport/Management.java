package transport;

import exceptions.DeliveryException;
import exceptions.ManagementException;
import hr.ICustomer;
import hr.IDestination;
import hr.IDriver;

import java.util.ArrayList;
import java.util.List;

public class Management implements IManagement {

    /* Tens de pensar nesta classe management como que o banco de dados de toda a aplicaçao, logo precisas de guardas aqui todos os dados da mesma,
    *
    * Daí ter criado um array de items, veiculos, drivers e deliveries... nao sei se é tudo mas acho que sim*/
    List<IItem> Items = new ArrayList<IItem>();
    List<IVehicle> Vehicles = new ArrayList<IVehicle>();
    List<IDriver> Drivers = new ArrayList<IDriver>();
    List<IDelivery> Deliveries = new ArrayList<IDelivery>();


    /* E também tens de pensar que nao pode haver um inventario infinito, logo cria-se variaveis que definem o numero maximo de cada coisa*/
    /* Estes valores nao estao no enunciado, inventei valores, verifica melhor no enunciado mas acho que nao estabelecem lá*/
    private static int MAXITEMS = 50;
    private static int MAXVEHICLES = 20;
    private static int MAXDRIVERS = 20;

    /* O caso das deliveries nao se aplica um valor maximo... */

    @Override
    public boolean addItem(IItem var1) throws ManagementException {
        // com os valores maximos definidos e com o container de items o array (Items) consegues saber se é true ou false adicionar o item

        if (Items.size() >= MAXITEMS)
            return false;
        else {
            Items.add(var1);
            return true;
        }
    }

    @Override
    public boolean removeItem(IItem var1) throws ManagementException {

        return Items.remove(var1);
    }

    @Override
    public IItem[] getItems() {

        /* Amanha lembra-me para te explicar isto */
        IItem[] ItemsArray = new IItem[ Items.size() ];
        Items.toArray( ItemsArray );
        /**/
        return ItemsArray;
    }

    @Override
    public IItem[] getItems(ICustomer var1) {

        /* Aqui tens de fazer como em cima mas tens que returnar só os items que tem o Customer igual ao var1 */
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(IDestination var1) {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(TransportationTypes var1) {
        return new IItem[0];
    }

    @Override
    public IItem[] getItems(ItemStatus var1) {
        return new IItem[0];
    }

    @Override
    public boolean addVehicle(IVehicle var1) throws ManagementException {

        if (Vehicles.size() >= MAXVEHICLES)
            return false;
        else {
            Vehicles.add(var1);
            return true;
        }
    }

    @Override
    public boolean addDriver(IDriver var1) throws ManagementException {

        if (Drivers.size() >= MAXDRIVERS)
            return false;
        else {
            Drivers.add(var1);
            return true;
        }
    }

    @Override
    public boolean removeDriver(IDriver var1) throws ManagementException {
        /* Com o exemplo do removeItem consegues fazer os outros removes*/
        return true;
    }

    @Override
    public boolean removeVehicle(IVehicle var1) throws ManagementException {
        return false;
    }

    @Override
    public IVehicle[] getFleet() {

        IVehicle[] VehiclesArray = new IVehicle[ Vehicles.size()];
        Vehicles.toArray(VehiclesArray);
        return VehiclesArray;
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1) {
        return new IVehicle[0];
    }

    @Override
    public IVehicle[] getFleet(TransportationTypes var1) {
        return new IVehicle[0];
    }

    @Override
    public IVehicle[] getFleet(VehicleStatus var1, TransportationTypes var2) {
        return new IVehicle[0];
    }

    @Override
    public boolean addDelivery(IDelivery var1) throws ManagementException {
        return false;
    }

    @Override
    public void deliveredItem(String var1, String var2) throws Exception {

    }

    @Override
    public void deliveredItem(String var1, IDestination var2) throws Exception {

    }

    @Override
    public ItemStatus checkItemStatus(String var1) throws Exception {
        return null;
    }

    @Override
    public void startDelivery(String var1) throws DeliveryException {

    }

    @Override
    public void stopDelivery(String var1) throws DeliveryException {

    }
}
