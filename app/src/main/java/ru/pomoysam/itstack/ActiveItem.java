package ru.pomoysam.itstack;

public class ActiveItem {

    String sizeMinuts;
    String sizeCoins;
    String date;
    String qrCode;
    String qrCodeID;
    int image;

    ActiveItem(String _describe,String _sizeCoins,String _date,String _qrCode,String _qrCodeID, int _image) {
        sizeMinuts = _describe;
        sizeCoins = _sizeCoins;
        date = _date;
        qrCode = _qrCode;
        qrCodeID = _qrCodeID;

        image = _image;

    }
}
