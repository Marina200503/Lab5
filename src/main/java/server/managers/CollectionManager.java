package server.managers;

import lib.models.Difficulty;
import lib.models.LabWork;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * оперирует коллекцией
 */
public class CollectionManager {
    private long currentId = 1;
    private LinkedList<LabWork> labWorks = new LinkedList<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();


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
        lock.writeLock().lock();
        try {
            if (labWork == null) return false;
            labWorks.add(labWork);
            return true;
        } finally
        {
            lock.writeLock().unlock();
        }
    }
    /**
     * считает количество лабораторных работ, сложность которых больше заданной
     *
     * @param difficulty - сложность лабораторной работы
     * @return количество лабораторных работ
     */
    public int countGreaterThanDifficulty(Difficulty difficulty) {
        lock.readLock().lock();
        try {
            return (int)(labWorks
                    .stream()
                    .filter(e -> (e.getDifficulty().compareTo(difficulty) > 0))
                    .count());
        } finally {
            lock.readLock().unlock();
        }
    }
    /**
     * Добавляет элемент по заданному id
     *
     * @param id - заданный идентификатор
     * @return объект лабораторной работы
     */
    public LabWork byId(long id) {
        lock.writeLock().lock();
        try {
            return labWorks
                    .stream()
                    .filter(e -> e.getId() == id) //e.getId() = id если tru, возвращает, иначе в этом стриме его не существует
                    .findFirst()//вытаскиваем
                    .orElse(null); //возвращаем (если есть), иначе null
        } finally
        {
            lock.writeLock().unlock();
        }
    }
    /**
     * Возвращает не занятый id
     * @return свободный id
     */
    public long getFreeId() {
        while (byId(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }
    /**
     * Сортерует лабораторные работы по сложности
     * @return отсортированную сложность
     */
    public List<Difficulty> getSortedByDifficulty() {
        lock.readLock().lock();
        try {
            return labWorks
                    .stream()
                    .sorted(Comparator.reverseOrder())
                    .map(lw -> lw.getDifficulty())
                    .toList();
        } finally {
            lock.readLock().unlock();
        }

    }

    /**
     * загружает коллекцию из файла
     *
     * @return true в случае успеха
     */
    public boolean loadCollection() {
        labWorks.clear();

        labWorks = SQLManager.selectAllData();
        lastInitTime = LocalDateTime.now();
        return true;
    }
    /**
     * Проверяет коллекцию лабораторных работ на пустоту
     * @return строковое представление лабораторных работ в коллекции
     */
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