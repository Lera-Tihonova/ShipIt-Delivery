import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private List<T> parcels;
    private int maxWeight;
    private int currentWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
        this.parcels = new ArrayList<>();
        this.currentWeight = 0;
    }

    public void addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() <= maxWeight) {
            parcels.add(parcel);
            currentWeight += parcel.getWeight();
            System.out.println("Посылка <<" + parcel.getDescription() + ">> добавлена в коробку");
        } else {
            System.out.println("Ошибка: превышен максимальный вес коробки! Посылка <<" +
                    parcel.getDescription() + ">> не добавлена");
        }
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getParcelCount() {
        return parcels.size();
    }
}