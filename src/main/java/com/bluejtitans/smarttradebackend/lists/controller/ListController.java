package com.bluejtitans.smarttradebackend.lists.controller;

import com.bluejtitans.smarttradebackend.lists.DTO.*;
import com.bluejtitans.smarttradebackend.lists.model.*;
import com.bluejtitans.smarttradebackend.lists.repository.GiftPersonRepository;
import com.bluejtitans.smarttradebackend.lists.repository.ShoppingCartRepository;
import com.bluejtitans.smarttradebackend.lists.service.*;
import com.bluejtitans.smarttradebackend.products.repository.ProductAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/clients/{clientId}/lists")
public class ListController {
    private final ProductAvailabilityRepository productAvailabilityRepository;
    private final GiftPersonRepository giftPersonRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ListService listService;
    @Autowired
    public ListController(ProductAvailabilityRepository productAvailabilityRepository, ShoppingCartRepository shoppingCartRepository, GiftPersonRepository giftPersonRepository, ListService listService) {
        this.productAvailabilityRepository = productAvailabilityRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.giftPersonRepository = giftPersonRepository;
        this.listService = listService;
    }
    @GetMapping("/{listType}")
    public ResponseEntity<?> getList(@PathVariable String clientId, @PathVariable String listType) {
        try {
            ProductList list = listService.getList(clientId, listType.toLowerCase());
            ListResponseDTO response = new ListResponseDTO();
            listService.setResponseDTO(response, list, listType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{listType}/products")
    public ResponseEntity<?> addProductToList(
            @PathVariable String clientId,
            @PathVariable String listType,
            @RequestBody ListRequestDTO request) {

        IListStrategy strategy;
        ProductList list;
        try{
             list = listService.getList(clientId, listType);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        //Determines the Strategy to use depending on the type of list
        switch (listType) {
            case "wishlist":
                Wishlist wishlist = (Wishlist) list;
                strategy = new WishlistStrategy(productAvailabilityRepository, wishlist);
                break;
            case "savedforlater":
                SavedForLater savedForLater = (SavedForLater) list;
                strategy = new SavedForLaterStrategy(productAvailabilityRepository, savedForLater);
                break;
            case "shoppingcart":
                ShoppingCart shoppingCart = (ShoppingCart) list;
                strategy = new ShoppingCartStrategy(productAvailabilityRepository, shoppingCartRepository, shoppingCart);
                break;
            case "giftlist":
                GiftList giftList = (GiftList) list;
                strategy = new GiftListStrategy(productAvailabilityRepository, giftPersonRepository, giftList);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        try {
            ProductList result = listService.addProduct(strategy, request);
            ListResponseDTO response = new ListResponseDTO();
            listService.setResponseDTO(response, result, listType);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Wrong RequestDTO");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{listType}/products")
    public ResponseEntity<?> deleteProductFromList(
            @PathVariable String clientId,
            @PathVariable String listType,
            @RequestBody ListRequestDTO request) {

        IListStrategy strategy;
        ProductList list;
        try{
            list = listService.getList(clientId, listType);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        //Determines the Strategy to use depending on the type of list
        switch (listType) {
            case "wishlist":
                Wishlist wishlist = (Wishlist) list;
                strategy = new WishlistStrategy(productAvailabilityRepository, wishlist);
                break;
            case "savedforlater":
                SavedForLater savedForLater = (SavedForLater) list;
                strategy = new SavedForLaterStrategy(productAvailabilityRepository, savedForLater);
                break;
            case "shoppingcart":
                ShoppingCart shoppingCart = (ShoppingCart) list;
                strategy = new ShoppingCartStrategy(productAvailabilityRepository, shoppingCartRepository, shoppingCart);
                break;
            case "giftlist":
                GiftList giftList = (GiftList) list;
                strategy = new GiftListStrategy(productAvailabilityRepository, giftPersonRepository, giftList);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        try {
            ProductList result = listService.removeProduct(strategy, request);
            ListResponseDTO response = new ListResponseDTO();
            listService.setResponseDTO(response, result, listType);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Wrong RequestDTO");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}