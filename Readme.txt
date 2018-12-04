Tro choi BomberMan duoc code bang JavaFx boi Nguyen Dat Ngoc Anh va Dinh Quang Binh

**** Gioi thieu tro choi ****
Trong phan menu tro choi co 2 muc la New Game va High Scores. Trong muc New Game, tro choi se co 3 level. Level cang cao se
tang dan do kho, cu the:
  lv1: player co 3 mang, co 8 mob , gioi han thoi gian 5 phut
  lv2: player co 3 mang, co 9 mob (them mob slime co 2 mang), gioi han thoi gian 4p30
  lv3: player co 2 mang, co 10 mob (them 2 mob slime), gioi han thoi gian 4p10

Cac man choi se co toi da 3 sao. Dieu kien de dat duoc cac sao:
  1 sao: hoan thanh man choi
  2 sao: hoan thanh man choi chi trong vong nua thoi gian
  3 sao: khong mat mang nao

Trong cac man choi, co 2 loai wall: wall co dinh (khong the bi pha) va wall random (co the pha)
Cac wall random duoc set = ham randomWall se tao ra cac map khac nhau. (Xem ham trong FXMLDocumentController.java)
Cac mob cung duoc set tai vi tri random (tat nhien la phai khac vi tri cac wall) (Xem ham setRandomMob trong FXMLDocumentController)
Huong di chuyen cua mob cung la random (khong theo quy luat). (Xem cac ham setAllMobAnimation, setMobPath)

Trong game player co the tim thay cac buff item khi pha tuong. Game co 3 loai buff:
  Buff lv bomb: khien bomb dai hon
  Buff speed: x2 toc do di chuyen
  Freeze: dong bang toan bo mob trong thoi gian 6s(trong thoi gian nay mob se khong the di chuyen) 
Luu y: buff lv bomb va buff speed se mat di neu player mat mang
Nhan F de thuc hien dong bang. Dau moi man choi Player se duoc nhan buff Freeze 1 lan (toi da 2 lan freeze trong 1 level)

Dieu kien de hoan thanh level la phai giet het mob va tim ra duoc canh cua. 
Cach tinh diem moi man choi nhu sau:
   Giet mob Lv1: (co 1 mang) duoc 100 diem
   Giet mob lv2: (co 2 mang) duoc 200 diem
   Bonus point = thoi gian con lai sau khi da hoan thanh man choi

Sau khi hoan than man choi, diem so cao nhat va sao dat duoc se duoc luu tai HighScores o ngoai Menu
Trong khi choi neu muon choi man khac co the nhan Esc -> ReturnMenu de chon man choi khac

Co the xem full gameplay cua game tai link drive duoi day: 
https://drive.google.com/open?id=1nPdTUtcYvV8ygBa0G5pI39mCmCBeujrb