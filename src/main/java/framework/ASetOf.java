package framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableSet.copyOf;
import static java.util.Objects.requireNonNull;

public abstract class ASetOf<T> implements Iterable<T> {

    protected ImmutableSet<T> set;

    public ASetOf() {
        this.set = ImmutableSet.of();
    }

    public ASetOf(ImmutableSet<T> set) {
        this.set = requireNonNull(set);
    }

    public ASetOf(Set<T> set) {
        this.set = copyOf(requireNonNull(set));
    }

    public ASetOf(List<T> list) {
        this.set = copyOf(requireNonNull(list));
    }

    public ASetOf(T[] arrayOfT) {
        this.set = copyOf(requireNonNull(arrayOfT));
    }

    public ASetOf(Iterable<T> iterable) {
        this.set = ImmutableSet.copyOf(iterable);
    }

    public static <T, Sub extends ASetOf<T>> Sub requireNotEmpty(Sub sub) {
        return requireNotEmpty(sub, null);
    }

    public static <T, Sub extends ASetOf<T>> Sub requireNotNullNotEmpty(Sub sub, String msg) {
        if (sub == null)
            throw new IllegalArgumentException(msg);
        return requireNotEmpty(sub, msg);
    }

    public static <T, Sub extends ASetOf<T>> Sub requireNotEmpty(Sub sub, String msg) {
        if (sub.isEmpty())
            throw new IllegalArgumentException(msg);
        return sub;
    }

    public abstract <Sub extends ASetOf<T>> Sub cons(Set<T> newSet);

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    public <Sub extends ASetOf<T>> Sub add(T t) {
        return cons(new ImmutableSet.Builder<T>().addAll(set).add(t).build());
    }

    public <Sub extends ASetOf<T>> Sub addAll(Sub sub) {
        return cons(new ImmutableSet.Builder<T>().addAll(set).addAll(sub.set).build());
    }

    public <Sub extends ASetOf<T>> Sub remove(T t) {
        return filter(e -> !e.equals(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASetOf<?> aSetOf = (ASetOf<?>) o;
        return set.equals(aSetOf.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    public boolean contains(T t) {
        return set.contains(t);
    }

    public <Sub extends ASetOf<T>> boolean containsAll(Sub sub) {
        return sub.minus(this).isEmpty();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public int size() {
        return set.size();
    }

    public Optional<T> findAny() {
        return stream().findAny();
    }

    public Optional<T> first() {
        return stream().findFirst();
    }

    public T first(T t) {
        return first().orElse(t);
    }

    public T firstOrNull() {
        return first().orElse(null);
    }

    private void checkUnique() {
        if (set.size() > 1)
            throw new IllegalStateException("this contains more than one element");
    }

    public Optional<T> unique() {
        checkUnique();
        return first();
    }

    public T unique(T t) {
        checkUnique();
        return first(t);
    }

    public T uniqueOrNull() {
        checkUnique();
        return firstOrNull();
    }

    public Stream<T> stream() {
        return set.stream();
    }

    public Stream<T> streamFilter(Predicate<T> predicate) {
        return set.stream().filter(predicate);
    }

    public <Sub extends ASetOf<T>> Sub filter(Predicate<T> predicate) {
        return cons(set.stream().filter(predicate).collect(Collectors.toSet()));
    }

    public void each(Consumer<T> consumer) {
        set.stream().forEach(consumer);
    }

    public Optional<T> find(T t) {
        return streamFilter(e -> e.equals(t)).findAny();
    }

    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    public Set<T> toSet() {
        return set;
    }

    public <Sub extends ASetOf<T>, R> Set<R> map(Function<T, R> mapper) {
        return set.stream().map(mapper).collect(Collectors.toSet());
    }

    public <Sub extends ASetOf<T>> Sub minus(Sub sub) {
        return filter(e -> !sub.contains(e));
    }

    public <Sub extends ASetOf<T>> Sub union(Sub sub) {
        return addAll(sub);
    }

    public <Sub extends ASetOf<T>> Sub inter(Sub sub) {
        return filter(e -> sub.contains(e));
    }
}
