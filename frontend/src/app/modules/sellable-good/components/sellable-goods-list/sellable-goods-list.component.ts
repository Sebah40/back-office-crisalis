import { Component, OnInit } from '@angular/core';
import { SellableGoodService } from '../../services/sellable-good.service';
import { SellableGood } from '../../model/sellable-good.model';
import { NavigationExtras, Router } from '@angular/router';
import { SweetAlertService } from 'src/app/modules/shared/service/sweet-alert.service';

@Component({
  selector: 'app-sellable-goods-list',
  templateUrl: './sellable-goods-list.component.html',
  styleUrls: ['./sellable-goods-list.component.css'],
})
export class SellableGoodsListComponent implements OnInit {
  public sellableGoodListData: SellableGood[] = [];
  public editableValue: boolean = true;

  constructor(
    public sellableGoodService: SellableGoodService,
    private router: Router,
    private sweet: SweetAlertService
  ) {}

  ngOnInit(): void {
    this.sellableGoodService.sellableGoodListData$.subscribe((goods) => {
      this.sellableGoodListData = goods;
    });

    this.sellableGoodService.getAll().subscribe((goods) => {
      this.sellableGoodListData = goods.filter((good) => good.active);
    });
  }

  disableEntity(sellableGood: SellableGood) {
    this.sellableGoodService.disable(sellableGood).subscribe({
      next: (response: any) => {
        this.sellableGoodService.updateSellableGoodListData();
        this.sweet.showAlert(response.mensaje, 'success');
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }

  createEntity() {
    this.router.navigate(['good/create']);
  }

  editEntity(sellableGood: SellableGood) {
    const navigationExtras: NavigationExtras = {
      state: sellableGood,
    };
    this.router.navigate(['good/edit'], navigationExtras);
  }
}
