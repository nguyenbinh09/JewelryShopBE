package com.example.JewelryShop.services;

import com.example.JewelryShop.dtos.JewelryItemDTO;
import com.example.JewelryShop.dtos.OptionDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Category;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.models.Review;
import com.example.JewelryShop.repositories.CategoryRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JewelryServiceTest {

    @Mock
    private JewelryItemRepository jewelryItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OptionService optionService;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private JewelryItemService jewelryService;

    @Test
    void getJewelryItems_WhenItemsExist_ReturnList() {
        // Arrange
        JewelryItem item = new JewelryItem();
        when(jewelryItemRepository.findAll()).thenReturn(List.of(item));

        // Act
        List<JewelryItem> result = jewelryService.getJewelryItems();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getJewelryItems_WhenNoItemsExist_ThrowNotFoundException() {
        // Arrange
        when(jewelryItemRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> jewelryService.getJewelryItems());
    }

    @Test
    void getJewelryItemById_WhenItemExists_ReturnItem() {
        // Arrange
        Long id = 1L;
        JewelryItem item = new JewelryItem();
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.of(item));

        // Act
        JewelryItem result = jewelryService.getJewelryItemById(id);

        // Assert
        assertNotNull(result);
        assertEquals(item, result);
    }

    @Test
    void getJewelryItemById_WhenItemDoesNotExist_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> jewelryService.getJewelryItemById(id));
    }

    @Test
    void updateJewelryItem_ValidInput_ReturnOkResponse() {
        // Arrange
        Long id = 1L;
        JewelryItemDTO jewelryItemDTO = new JewelryItemDTO();
        JewelryItem oldJewelryItem = new JewelryItem();
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.of(oldJewelryItem));

        // Act
        ResponseEntity<?> response = jewelryService.updateJewelryItem(id, jewelryItemDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Jewelry updated successfully"), response);
        verify(jewelryItemRepository, times(1)).save(any(JewelryItem.class));
    }

    @Test
    void updateJewelryItem_WhenItemDoesNotExist_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;
        JewelryItemDTO jewelryItemDTO = new JewelryItemDTO();
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> jewelryService.updateJewelryItem(id, jewelryItemDTO));
    }

    @Test
    void deleteJewelryItem_WhenItemExists_ReturnOkResponse() {
        // Arrange
        Long id = 1L;
        JewelryItem jewelryItem = new JewelryItem();
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.of(jewelryItem));

        // Act
        ResponseEntity<?> response = jewelryService.deleteJewelryItem(id);

        // Assert
        assertEquals(ResponseEntity.ok("Jewelry deleted successfully"), response);
        assertTrue(jewelryItem.getIs_deleted());
        verify(jewelryItemRepository, times(1)).save(jewelryItem);
    }

    @Test
    void deleteJewelryItem_WhenItemDoesNotExist_ThrowNotFoundException() {
        // Arrange
        Long id = 1L;
        when(jewelryItemRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> jewelryService.deleteJewelryItem(id));
    }

    @Test
    void updateJewelryRating_ValidInput_UpdatesRating() {
        // Arrange
        JewelryItem jewelryItem = new JewelryItem();
        Review review1 = new Review();
        review1.setRating(4.0F);
        Review review2 = new Review();
        review2.setRating(5.0F);
        jewelryItem.setReviews(List.of(review1, review2));
        when(jewelryItemRepository.save(jewelryItem)).thenReturn(jewelryItem);

        // Act
        jewelryService.updateJewelryRating(jewelryItem);

        // Assert
        assertEquals(4.5, jewelryItem.getRating());
        verify(jewelryItemRepository, times(1)).save(jewelryItem);
    }
}

