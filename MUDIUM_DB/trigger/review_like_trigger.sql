DELIMITER $$

CREATE TRIGGER trg_update_review_like
AFTER INSERT ON TBL_REVIEW_LIKE
FOR EACH ROW
BEGIN
    -- 리뷰에 대한 좋아요가 추가될 때, TBL_REVIEW 테이블의 like 값을 +1 증가
    UPDATE TBL_REVIEW
    SET `like` = `like` + 1
    WHERE review_id = NEW.review_id;
END $$

CREATE TRIGGER trg_delete_review_like
AFTER DELETE ON TBL_REVIEW_LIKE
FOR EACH ROW
BEGIN
    -- 리뷰에 대한 좋아요가 삭제될 때, TBL_REVIEW 테이블의 like 값을 -1 감소
    UPDATE TBL_REVIEW
    SET `like` = `like` - 1
    WHERE review_id = OLD.review_id;
END $$

DELIMITER ;
