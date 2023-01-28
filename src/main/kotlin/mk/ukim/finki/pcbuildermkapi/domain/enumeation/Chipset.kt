package mk.ukim.finki.pcbuildermkapi.domain.enumeation

enum class Chipset {
// INTEL
    // 1156
    H55, P55, H57, Q57,
    // SANDY BRIDGE
    H61, B65, Q65, P67, H67, Q67, Z68,
    // IVY BRIDGE
    B75, Q75, Z75, H77, Q77, Z77,
    // HASWELL
    H81, B85, Q85, Q87, H87, Z87,
    // HASWELL REFRESH
    H97, Z97,
    // SKYLAKE
    H110, B150, Q150, H170, Q170, Z170,
    // KABY LAKE
    B250, Q250, H270, Q270, Z270,
    // COFFE LAKE
    H310, B360, B365, H370, Q370, Z370, Z390,
    // COMMET LAKE
    H410, B460, H470, Q470, Z490,
    // ROCKET LAKE
    H510, B560, H570, Z590,
    // ALDER LAKE
    H610, B660, H670, Z690,
    // RAPTOR LAKE
    B760, H770, Z790,
// AMD
    // ZEN
    A320, B350, X370,
    // ZEN 2
    B450, X470,
    // ZEN 3
    A520, B550, X570,
    // ZEN 4
    A620, B650, B650E, X670, X670E
}