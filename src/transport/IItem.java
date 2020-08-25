package transport;

import hr.ICustomer;
import hr.IDestination;

public interface IItem extends IBox {
    String getReference();

    String getDescription();

    void setDescription(String var1);

    TransportationTypes[] getTransportationTypes();

    ICustomer getCustomer();

    IDestination getDestination();

    double getWeight();

    ItemStatus getStatus();

    void setStatus(ItemStatus var1);
}

