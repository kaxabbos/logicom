package com.logicom.controller.main;

import com.logicom.model.Ordering;
import com.logicom.model.Product;
import com.logicom.model.StatProduct;
import com.logicom.model.enums.OrderingStatus;
import com.logicom.model.enums.PaymentType;
import com.logicom.model.enums.ProductStatus;
import com.logicom.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesDetails(Model model, Long idOrdering) {
        AddAttributes(model);
        model.addAttribute("details", orderingService.find(idOrdering).getDetails());
        model.addAttribute("ordering", orderingService.find(idOrdering));
    }

    protected void AddAttributesIndex(Model model) {
        AddAttributes(model);
    }

    protected void AddAttributesOrderDetails(Model model, Long idOrdering) {
        AddAttributes(model);
        model.addAttribute("orderingDetails", orderingService.find(idOrdering).getDetails());
        model.addAttribute("ordering", orderingService.find(idOrdering));
        List<Product> temp = productService.findAllByOrderByName();
        List<Product> products = temp.stream().filter(product -> product.getQuantity() != 0).toList();
        model.addAttribute("products", products);
    }

    protected void AddAttributesOrdering(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.NOT_RESERVED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("orderings", orderingList);
        model.addAttribute("clients", clientService.findAllByOrderByIdDesc());
    }

    protected void AddAttributesPayments(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.RESERVED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("payments", orderingList);
        model.addAttribute("paymentTypes", PaymentType.values());
    }

    protected void AddAttributesShipment(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.SHIPMENT);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("shipments", orderingList);
    }

    protected void AddAttributesShipped(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.SHIPPED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("shippeds", orderingList);
    }

    protected void AddAttributesWaiting(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.WAITING);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("waitings", orderingList);
    }

    protected void AddAttributesStatProduct(Model model, ProductStatus productStatus, String date) {
        AddAttributes(model);

        List<StatProduct> statProducts;

        if (productStatus == ProductStatus.ALL && (date == null || date.equals(""))) {
            statProducts = statProductService.findAllByOrderByIdDesc();
        } else if (productStatus == ProductStatus.ALL) {
            statProducts = statProductService.findAllByDateOrderByIdDesc(date);
        } else if (date == null || date.equals("")) {
            statProducts = statProductService.findAllByProductStatusOrderByIdDesc(productStatus);
        } else {
            statProducts = statProductService.findAllByProductStatusAndDateOrderByIdDesc(productStatus, date);
        }

        int max = 0;
        for (StatProduct i : statProducts) {
            if (i.getProductStatus() == ProductStatus.PRODUCED) max += i.getQuantity();
            if (i.getProductStatus() == ProductStatus.SHIPPED || i.getProductStatus() == ProductStatus.RESERVED)
                max -= i.getQuantity();
        }

        model.addAttribute("selectedStatus", productStatus);
        model.addAttribute("selectedDate", date);
        model.addAttribute("statProducts", statProducts);
        model.addAttribute("max", max);
        model.addAttribute("ProductStatus", ProductStatus.values());
    }

    protected void AddAttributesStatOrdering(Model model, OrderingStatus orderingStatus, String date) {
        AddAttributes(model);
        List<Ordering> orderingList;
        if (orderingStatus == OrderingStatus.ALL && (date == null || date.equals(""))) {
            orderingList = orderingService.findAllByOrderByIdDesc();
        } else if (orderingStatus == OrderingStatus.ALL) {
            orderingList = orderingService.findAllByDateOrderByIdDesc(date);
        } else if (date == null || date.equals("")) {
            orderingList = orderingService.findAllByOrderingStatusOrderByIdDesc(orderingStatus);
        } else {
            orderingList = orderingService.findAllByOrderingStatusAndDateOrderByIdDesc(orderingStatus, date);
        }

        model.addAttribute("orderings", orderingList);
        model.addAttribute("selectedStatus", orderingStatus);
        model.addAttribute("selectedDate", date);
        model.addAttribute("statuses", OrderingStatus.values());
    }

    protected void AddAttributesStat(Model model) {
        AddAttributes(model);
        List<Integer> status = new ArrayList<>();
        List<OrderingStatus> orderingStatusList = List.of(OrderingStatus.values());
        for (OrderingStatus orderingStatus : orderingStatusList) {
            int temp = orderingService.countByOrderingStatus(orderingStatus);
            status.add(temp);
            status.set(0, status.get(0) + temp);
        }
        model.addAttribute("orderingStatus", status);
    }

    protected void AddAttributesProducts(Model model) {
        AddAttributes(model);
        List<Product> products = productService.findAllByOrderByIdDesc();
        int quantity = products.stream().reduce(0, ((integer, product) -> integer + product.getQuantity()), Integer::sum);
        model.addAttribute("quantity", quantity);
        model.addAttribute("products", products);
    }

    protected void AddAttributesClients(Model model) {
        AddAttributes(model);
        model.addAttribute("clients", clientService.findAllByOrderByIdDesc());
    }

    protected void AddAttributesProfiles(Model model) {
        AddAttributes(model);
        model.addAttribute("roles", Role.values());
        model.addAttribute("users", usersService.findAllByOrderByRole());
    }

    protected void AddAttributesAddUser(Model model) {
        AddAttributes(model);
        model.addAttribute("roles", Role.values());
    }
}
