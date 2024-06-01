package com.example.JewelryShop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.JewelryShop.dtos.ReviewDTO;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Customer;
import com.example.JewelryShop.models.JewelryItem;
import com.example.JewelryShop.models.Review;
import com.example.JewelryShop.models.User;
import com.example.JewelryShop.repositories.CustomerRepository;
import com.example.JewelryShop.repositories.JewelryItemRepository;
import com.example.JewelryShop.repositories.ReviewRepository;
import com.example.JewelryShop.repositories.UserRepository;
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
class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JewelryItemRepository jewelryItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private JewelryItemService jewelryItemService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void addNewReview_ValidInput_ReturnsOkResponse() throws IOException {
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        List<MultipartFile> images = List.of(mock(MultipartFile.class));
        User user = new User();
        JewelryItem jewelryItem = new JewelryItem();
        Review review = new Review();

        when(reviewDTO.getUser_id()).thenReturn(1L);
        when(reviewDTO.getJewelry_item_id()).thenReturn(1L);
        when(reviewDTO.toEntity()).thenReturn(review);
        when(userRepository.findById(reviewDTO.getUser_id())).thenReturn(Optional.of(user));
        when(jewelryItemRepository.findById(reviewDTO.getJewelry_item_id())).thenReturn(Optional.of(jewelryItem));
        when(cloudinaryService.upload(any(MultipartFile.class))).thenReturn("image-url");

        ResponseEntity<?> response = reviewService.addNewReview(reviewDTO, images);

        assertEquals(ResponseEntity.ok().body("Review added successfully"), response);
        verify(reviewRepository, times(1)).save(review);
        verify(jewelryItemService, times(1)).updateJewelryRating(jewelryItem);
    }

    @Test
    void addNewReview_InvalidCustomerId_ThrowsNotFoundException() {
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewDTO.getUser_id()).thenReturn(1L);
        when(userRepository.findById(reviewDTO.getUser_id())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.addNewReview(reviewDTO, null));
    }

    @Test
    void addNewReview_InvalidJewelryItemId_ThrowsNotFoundException() {
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewDTO.getUser_id()).thenReturn(1L);
        when(reviewDTO.getJewelry_item_id()).thenReturn(1L);
        when(userRepository.findById(reviewDTO.getUser_id())).thenReturn(Optional.of(new User()));
        when(jewelryItemRepository.findById(reviewDTO.getJewelry_item_id())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.addNewReview(reviewDTO, null));
    }

    @Test
    void getReviewsByJewelryItemId_ValidId_ReturnsReviews() {
        Long jewelryItemId = 1L;
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewRepository.findByJewelryItemId(jewelryItemId)).thenReturn(reviews);

        List<Review> result = reviewService.getReviewsByJewelryItemId(jewelryItemId);

        assertEquals(reviews.size(), result.size());
        verify(reviewRepository, times(1)).findByJewelryItemId(jewelryItemId);
    }

    @Test
    void deleteReview_ValidId_ReturnsOkResponse() {
        Long reviewId = 1L;
        Review review = new Review();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ResponseEntity<?> response = reviewService.deleteReview(reviewId);

        assertEquals(ResponseEntity.ok().body("Review deleted successfully"), response);
        assertTrue(review.getIs_deleted());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void deleteReview_InvalidId_ThrowsNotFoundException() {
        Long reviewId = 1L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.deleteReview(reviewId));
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void updateReview_ValidId_ReturnsOkResponse() {
        Long reviewId = 1L;
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        Review review = new Review();
        JewelryItem jewelryItem = new JewelryItem();
        review.setJewelry_item(jewelryItem);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewDTO.getText()).thenReturn("Updated text");
        when(reviewDTO.getRating()).thenReturn(5F);

        ResponseEntity<?> response = reviewService.updateReview(reviewId, reviewDTO);

        assertEquals(ResponseEntity.ok().body("Review updated successfully"), response);
        assertEquals("Updated text", review.getText());
        assertEquals(5, review.getRating());
        verify(reviewRepository, times(1)).save(review);
        verify(jewelryItemService, times(1)).updateJewelryRating(jewelryItem);
    }

    @Test
    void updateReview_InvalidId_ThrowsNotFoundException() {
        Long reviewId = 1L;
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.updateReview(reviewId, reviewDTO));
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(0)).save(any(Review.class));
    }
}

