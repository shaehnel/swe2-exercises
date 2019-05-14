package ch.juventus.threads;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PersonStore {

    private Set<Person> persons = ConcurrentHashMap.newKeySet();

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void removePerson(Person person) {
        persons.remove(person);
    }

    public Set<Person> getPersonsByLastName(String lastName) {
        return persons
            .stream()
            .filter(p -> lastName.equals(p.getLastName()))
            .collect(Collectors.toSet());
    }

}
