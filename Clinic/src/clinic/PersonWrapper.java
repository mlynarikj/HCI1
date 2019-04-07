package clinic;

import model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonWrapper<T extends Person> {

    private T person;

    public PersonWrapper(T person) {
        this.person = person;
    }

    public T getPerson() {
        return person;
    }

    public static<R extends Person> List<PersonWrapper> convert(List<R> og){
        return og.stream().map(PersonWrapper::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return person.getName()+" "+person.getSurname();
    }
}
