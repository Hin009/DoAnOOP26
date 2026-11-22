
    public String getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(String maChamCong) {
        if (maChamCong == null || maChamCong.trim().isEmpty()
                || maChamCong.contains(";") || maChamCong.contains("\n")) {
            throw new IllegalArgumentException("Ma cham cong khong duoc de trong va khong duoc chua dau ';'.");
        }
        this.maChamCong = KiemTra.ma(maChamCong);