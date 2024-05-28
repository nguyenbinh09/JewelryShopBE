package com.example.JewelryShop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.JewelryShop.dtos.VariantUpdateDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.*;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.VariantOptionValueRepository;
import com.example.JewelryShop.repositories.VariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VariantServiceTest {

    @Mock
    private JewelryItemRepository jewelryItemRepository;

    @Mock
    private VariantRepository variantRepository;

    @Mock
    private VariantOptionValueRepository variantOptionValueRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private VariantService variantService;

    @Test
    void createVariantsForItem_ValidId_ReturnsOkResponse() {
        // Arrange
        Long jewelryItemId = 1L;
        JewelryItem jewelryItem = new JewelryItem();
        Option option = new Option();
        OptionValue optionValue = new OptionValue();
        option.setOptionValues(List.of(optionValue));
        jewelryItem.setOptions(List.of(option));
        when(jewelryItemRepository.findById(jewelryItemId)).thenReturn(Optional.of(jewelryItem));
        when(variantRepository.save(any(Variant.class))).thenReturn(new Variant());

        // Act
        ResponseEntity<?> response = variantService.createVariantsForItem(jewelryItemId);

        // Assert
        assertEquals(ResponseEntity.ok("Variants added successfully"), response);
        verify(jewelryItemRepository, times(1)).findById(jewelryItemId);
        verify(variantRepository, atLeastOnce()).save(any(Variant.class));
    }

    @Test
    void createVariantsForItem_InvalidId_ThrowsException() {
        // Arrange
        Long jewelryItemId = 1L;
        when(jewelryItemRepository.findById(jewelryItemId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            variantService.createVariantsForItem(jewelryItemId);
        });
        assertEquals("Jewelry item with id " + jewelryItemId + " does not exist", exception.getMessage());
    }

    @Test
    void getVariantByJewelryItemId_ValidId_ReturnsVariantList() {
        // Arrange
        Long jewelryItemId = 1L;
        List<Variant> variants = List.of(new Variant(), new Variant());
        when(variantRepository.findAllByJewelryItemId(jewelryItemId)).thenReturn(variants);

        // Act
        List<Variant> result = variantService.getVariantByJewelryItemId(jewelryItemId);

        // Assert
        assertEquals(variants, result);
        verify(variantRepository, times(1)).findAllByJewelryItemId(jewelryItemId);
    }

    @Test
    void updateVariant_ValidId_ReturnsOkResponse() {
        // Arrange
        Long variantId = 1L;
        Variant variant = new Variant();
        VariantUpdateDTO variantUpdateDTO = mock(VariantUpdateDTO.class);
        when(variantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        // Act
        ResponseEntity<?> response = variantService.updateVariant(variantId, variantUpdateDTO);

        // Assert
        assertEquals(ResponseEntity.ok("Variant updated successfully"), response);
        verify(variantUpdateDTO, times(1)).toEntity(variant);
        verify(variantRepository, times(1)).save(variant);
    }

    @Test
    void updateVariant_InvalidId_ThrowsException() {
        // Arrange
        Long variantId = 1L;
        VariantUpdateDTO variantUpdateDTO = mock(VariantUpdateDTO.class);
        when(variantRepository.findById(variantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            variantService.updateVariant(variantId, variantUpdateDTO);
        });
        assertEquals("Variant with id " + variantId + " does not exist", exception.getMessage());
    }

    @Test
    void uploadVariantImage_ValidId_ReturnsOkResponse() throws IOException {
        // Arrange
        Long variantId = 1L;
        Variant variant = new Variant();
        MultipartFile image = mock(MultipartFile.class);
        when(variantRepository.findById(variantId)).thenReturn(Optional.of(variant));
        when(cloudinaryService.upload(image)).thenReturn("http://image.url");

        // Act
        ResponseEntity<?> response = variantService.uploadVariantImage(variantId, List.of(image));

        // Assert
        assertEquals(ResponseEntity.ok("Variant image updated successfully"), response);
        verify(variantRepository, times(1)).findById(variantId);
        verify(cloudinaryService, times(1)).upload(image);
        verify(variantRepository, times(1)).save(variant);
    }

    @Test
    void uploadVariantImage_InvalidId_ThrowsException() {
        // Arrange
        Long variantId = 1L;
        MultipartFile image = mock(MultipartFile.class);
        when(variantRepository.findById(variantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            variantService.uploadVariantImage(variantId, List.of(image));
        });
        assertEquals("Variant with id " + variantId + " does not exist", exception.getMessage());
    }

    @Test
    void deleteVariant_ValidId_ReturnsOkResponse() {
        // Arrange
        Long variantId = 1L;
        Variant variant = new Variant();
        when(variantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        // Act
        ResponseEntity<?> response = variantService.deleteVariant(variantId);

        // Assert
        assertEquals(ResponseEntity.ok("Variant deleted successfully"), response);
        assertTrue(variant.getIs_deleted());
        verify(variantRepository, times(1)).save(variant);
    }

    @Test
    void deleteVariant_InvalidId_ThrowsException() {
        // Arrange
        Long variantId = 1L;
        when(variantRepository.findById(variantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            variantService.deleteVariant(variantId);
        });
        assertEquals("Variant with id " + variantId + " does not exist", exception.getMessage());
    }

    @Test
    void deleteVariantImage_ValidId_ReturnsOkResponse() {
        // Arrange
        Long variantId = 1L;
        Variant variant = new Variant();
        Image image = new Image();
        image.setId(1L);
        variant.setImages(List.of(image));
        when(variantRepository.findById(variantId)).thenReturn(Optional.of(variant));

        // Act
        ResponseEntity<?> response = variantService.deleteVariantImage(variantId, List.of(1L));

        // Assert
        assertEquals(ResponseEntity.ok("Images deleted successfully"), response);
        assertTrue(image.getIs_deleted());
        verify(variantRepository, times(1)).findById(variantId);
    }

    @Test
    void deleteVariantImage_InvalidId_ThrowsException() {
        // Arrange
        Long variantId = 1L;
        when(variantRepository.findById(variantId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            variantService.deleteVariantImage(variantId, List.of(1L));
        });
        assertEquals("Variant with id " + variantId + " does not exist", exception.getMessage());
    }
}

