package utils;
import model.BookBase;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortingUtils {

    public static List<BookBase> sortByPrice(List<BookBase> books) {
        return books.stream().sorted(Comparator.comparing(BookBase::getPrice)).collect(Collectors.toList());
    }

    public static List<BookBase> filterByPrice(List<BookBase> books, double minPrice) {

        Predicate<BookBase> priceFilter = book -> book.getPrice() >= minPrice;

        return books.stream().filter(priceFilter).collect(Collectors.toList());
    }
}
