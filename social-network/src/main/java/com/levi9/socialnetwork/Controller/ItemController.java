package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Service.ItemService;
import com.levi9.socialnetwork.dto.ItemDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO){

        ItemDTO updatedItem;

        try{
            updatedItem = itemService.updateItem(id, itemDTO);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id){
        try{
            itemService.deleteItem(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
