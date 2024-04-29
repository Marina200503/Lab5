package managers;

import models.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * оперирует коллекцией
 */
public class CollectionManager {
    private long currentId = 1;
    private LinkedList<LabWork> labWorks = new LinkedList<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;


    public CollectionManager() {
        this.lastInitTime = null;
        this.lastSaveTime = null;
    }

    /**
     * @return коллекция
     */
    public LinkedList<LabWork> getCollection() {
        return labWorks;
    }

    /**
     * @return последнее время инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return последнее время сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * добавляет лабораторную работу
     *
     * @param labWork - экземпляр лабораторной работы
     * @return успешность выполнения команды
     */
    public boolean add(LabWork labWork) {
        if (labWork == null) return false;
        labWorks.add(labWork);
        return true;
    }

    public LabWork byId(long id) {
        return labWorks
                .stream()
                .filter(e -> e.getId() == id) //e.getId() = id если tru, возвращает, иначе в этом стриме его не существует
                .findFirst()//вытаскиваем
                .orElse(null); //возвращаем (если есть), иначе null
    }

    public long getFreeId() {
        while (byId(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }

    public List<LabWork> getSortedByDifficulty() {
        return labWorks
                .stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    /**
     * загружает коллекцию из файла
     *
     * @return true в случае успеха
     */
    public boolean loadCollection() {
        labWorks.clear();

        labWorks = (LinkedList<LabWork>) new JsonManager().load();
        lastInitTime = LocalDateTime.now();
        return true;
    }

    @Override
    public String toString() {
        if (labWorks.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (LabWork labWork : labWorks) {
            info.append(labWork + "\n\n");
        }
        return info.toString().trim();
    }
}