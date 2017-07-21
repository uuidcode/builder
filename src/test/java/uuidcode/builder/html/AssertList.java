package uuidcode.builder.html;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Consumer;

public class AssertList<T> {
    private List<T> list;

    public AssertList(List<T> list) {
        this.list = list;
    }

    public static <T> AssertList<T> of(List<T> list) {
        return new AssertList<>(list);
    }

    public AssertList<T> hasSize(int size) {
        assertThat(this.list).hasSize(size);
        return this;
    }


    public AssertList<T> satisfies(int i , Consumer<T> checker) {
        T t = this.list.get(i);
        checker.accept(t);
        return this;
    }
}
