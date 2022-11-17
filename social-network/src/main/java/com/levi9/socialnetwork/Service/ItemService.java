package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceConflictException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Item;
import com.levi9.socialnetwork.Repository.ItemRepository;
import com.levi9.socialnetwork.dto.ItemDTO;
import com.levi9.socialnetwork.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemDTO getItemById(Long id) throws ResourceNotFoundException {
        return itemRepository.findItemById(id)
                .map(ItemMapper::mapEntityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + "was not found"));
    }

    public Long createItem(ItemDTO itemDTO){
        return itemRepository.save(ItemMapper.mapDTOToEntity(itemDTO)).getId();
    }

    @Transactional
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) throws ResourceNotFoundException {
        Item item = itemRepository.findItemById(id)
                .map(searchedItem -> searchedItem)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + "was not found"));

        item.setLink(itemDTO.getLink());

        itemRepository.save(item);

        return ItemMapper.mapEntityToDTO(item);
    }

    @Transactional
    public void deleteItem(Long id) throws ResourceNotFoundException {
        itemRepository.findItemById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + id + "was not found"));

        itemRepository.deleteById(id);
    }
}
