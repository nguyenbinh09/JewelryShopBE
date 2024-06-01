package com.example.JewelryShop.controllers;

import com.example.JewelryShop.dtos.ReviewDTO;
import com.example.JewelryShop.exceptions.InternalServerErrorException;
import com.example.JewelryShop.exceptions.NotFoundException;
import com.example.JewelryShop.models.Review;
import com.example.JewelryShop.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/comment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewReview(@RequestPart("reviewJson") String reviewJson, @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ReviewDTO reviewDTO = objectMapper.readValue(reviewJson, ReviewDTO.class);
            return reviewService.addNewReview(reviewDTO, images);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/byJewelryId/{jewelry_id}")
    public List<Review> getReviewsByJewelryItemId(@PathVariable Long jewelry_id) {
        try {
            return reviewService.getReviewsByJewelryItemId(jewelry_id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        try {
            return reviewService.deleteReview(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        try {
            return reviewService.updateReview(id, reviewDTO);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
