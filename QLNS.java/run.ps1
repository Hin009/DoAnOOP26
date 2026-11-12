param(
    [switch]$Test,
    [switch]$Demo,
    [switch]$Trong,
    [string]$Data = 'data/qlns.txt'
)
$ErrorActionPreference = 'Stop'
Push-Location $PSScriptRoot
try {
    New-Item -ItemType Directory -Force build | Out-Null
    $sources = Get-ChildItem -File -Filter '*.java' | ForEach-Object FullName
    & javac -encoding UTF-8 -d build $sources
    if ($LASTEXITCODE -ne 0) { throw 'Bien dich that bai.' }
    if ($Test) {
        & java -cp build TestHeThong
    } else {
        $appArgs = @('--data', $Data)
        if ($Demo) { $appArgs += '--demo' }
        if ($Trong) { $appArgs += '--trong' }
        & java '-Dfile.encoding=UTF-8' -cp build Main @appArgs
    }
    if ($LASTEXITCODE -ne 0) { throw "Chuong trinh tra ve ma loi $LASTEXITCODE." }
} finally { Pop-Location }
