package myspringboot.reactive.r2dbc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class LambdaTest {

    /*
        Stream의 map()과 flatMap의 차이점 이해
     */
    @Test
    public void transformUsingStream() {
        List<Customer> customers = List.of(
                new Customer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
                new Customer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
                new Customer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
                new Customer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467"))
        );
        // email주소 목록 List<String>
        List<String> emailList = customers.stream() // Stream<Customer>
                                            .map(cust -> cust.getEmail()) // Stream<String>
                                            .collect(toList()); // List<String>

        emailList.forEach(System.out::println);

        // Method reference
        customers.stream()
                .map(Customer::getEmail)
                .collect(toList())
                .forEach(System.out::println);

        //map() : <R> Stream<R> map(Function<? super T,? extends R> mapper)
        List<List<String>> phoneList = customers.stream()  //Stream<Customer>
                .map(Customer::getPhoneNumbers) //Stream<List<String>>
                .collect(toList()); // List<List<String>>

        System.out.println("phoneList = " + phoneList);

        //flatMap : <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        List<String> phoneList2 = customers.stream() //Stream<Customer>
                                            .flatMap(customer -> customer.getPhoneNumbers().stream()) // Stream<Stream<List<String>>>
                                            .collect(toList());
        System.out.println("phoneList2 = " + phoneList2);
    }

    @Test
    public void lambdaTest() {
        // Functional Interface가 가진 추상메서드를 재정의할 때 람다식으로 작성하기
        // 1. Anonymous Inner class
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner class");
            }
        });
        t1.start();

        // 2. Lambda Expression
        Thread t2 = new Thread(() -> System.out.print("Lambda Expression"));
        t2.start();

        /*
            java.util.function에 제공하는 함수형 인터페이스
            Consumer - void accept(T t)
            Predicate - boolean test(T t)
            Supplier - T get()
            Function - R apply(T t)
            Operator - BinaryOperator - function의 apply 메소드와 동일( R apply(T t, U u) )
                     - UnaryOperator - function의 apply 메소드와 동일( R apply(T t) )
         */
        List<String> stringList = List.of("abc", "java", "boot");
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s + " + s);
            }
        });

        // Lambda Expression
        stringList.forEach(val -> System.out.println(val));

        // Method Reference
        stringList.forEach(System.out::println);
    }
}
